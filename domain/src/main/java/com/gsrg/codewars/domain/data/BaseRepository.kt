package com.gsrg.codewars.domain.data

import com.google.gson.Gson
import com.gsrg.codewars.domain.model.ErrorResponse
import com.gsrg.codewars.domain.utils.TAG
import okhttp3.ResponseBody
import timber.log.Timber

abstract class BaseRepository {

    fun convertErrorResponseToString(code: Int, errorResponseBody: ResponseBody): String? {
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