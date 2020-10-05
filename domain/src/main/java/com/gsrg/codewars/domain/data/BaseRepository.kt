package com.gsrg.codewars.domain.data

import com.google.gson.Gson
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.model.ErrorResponse
import com.gsrg.codewars.domain.utils.TAG
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber

abstract class BaseRepository {

    fun takeCareOfError(response: Response<*>): Result.Error {
        return response.run {
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