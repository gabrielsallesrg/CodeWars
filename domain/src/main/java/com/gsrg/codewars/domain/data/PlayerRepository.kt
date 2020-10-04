package com.gsrg.codewars.domain.data

import com.google.gson.Gson
import com.gsrg.codewars.domain.api.CodeWarsApiService
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.model.ErrorResponse
import com.gsrg.codewars.domain.model.PlayerResponse
import com.gsrg.codewars.domain.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class PlayerRepository
@Inject constructor(private val codeWarsApiService: CodeWarsApiService) : IPlayerRepository {

    override fun getPlayerDetailsByName(name: String): Flow<Result<PlayerResponse>> = flow {
        emit(getPlayerDetailsByNameSuspend(name))
    }

    private suspend fun getPlayerDetailsByNameSuspend(name: String) =
        try {
            codeWarsApiService.searchPlayer(idOrUsername = name).run {
                Timber.tag(this@PlayerRepository.TAG()).d("%s -> %s", message(), body())
                if (isSuccessful && body() != null) {
                    Result.Success(data = body()!!)
                } else {
                    if (errorBody() is ResponseBody) {
                        val errorMessage: String = errorBody()?.run {
                            convertErrorResponseToString(code(), this)
                        } ?: code().toString() + " Unknown Error"

                        Result.Error(
                            Exception(),
                            if (errorMessage.isBlank()) code()
                                .toString() + message() else errorMessage
                        )
                    } else {
                        Result.Error(Exception(), code().toString() + message())
                    }
                }
            }
        } catch (exception: UnknownHostException) {
            Timber.tag(TAG()).e(exception)
            Result.Error(exception, "Something went wrong. Check your internet connection.")
        } catch (exception: Exception) {
            Timber.tag(TAG()).e(exception)
            Result.Error(exception, "Something went wrong.")
        }


    private fun convertErrorResponseToString(code: Int, errorResponseBody: ResponseBody): String? {
        val gson = Gson()
        try {
            val errorResponse: ErrorResponse? =
                gson.fromJson(errorResponseBody.charStream(), ErrorResponse::class.java)
            errorResponse?.reason?.let {
                return "$code $it"
            }
        } catch (exception: Exception) {
            Timber.tag(TAG()).e(exception)
        }
        return null
    }
}