package com.gsrg.codewars.domain.data.player

import com.gsrg.codewars.domain.api.CodeWarsApiService
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.data.BaseRepository
import com.gsrg.codewars.domain.model.PlayerResponse
import com.gsrg.codewars.domain.utils.TAG
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import timber.log.Timber
import java.net.UnknownHostException
import javax.inject.Inject

class PlayerRepository
@Inject constructor(private val codeWarsApiService: CodeWarsApiService) : BaseRepository(), IPlayerRepository {

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
}