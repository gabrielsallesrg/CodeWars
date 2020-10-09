package com.gsrg.codewars.domain

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException

class MockInterceptor(val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (BuildConfig.MOCK_RESPONSE) {
            val uri = chain.request().url.toUri().toString()
            val responseString = when {
                uri.contains("completed") -> loadJsonFromAssets("mock_challenge_completed_list.json")
                uri.contains("authored") -> loadJsonFromAssets("mock_authored_challenge_list.json")
                uri.contains("code-challenges") -> loadJsonFromAssets("mock_code_challenge_details.json")
                uri.contains("users") -> loadJsonFromAssets("mock_user.json")
                else -> "{}"
            }

            return chain.proceed(chain.request())
                .newBuilder()
                .code(200)
                .protocol(Protocol.HTTP_2)
                .message(responseString)
                .body(
                    responseString.toByteArray()
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
                .addHeader("content-type", "application/json")
                .build()
        } else {
            //just to be on safe side.
            throw IllegalAccessError(
                "MockInterceptor is only meant for Testing Purposes and " +
                        "bound to be used only with DEBUG mode"
            )
        }
    }

    private fun loadJsonFromAssets(fileName: String): String {
        return try {
            val inputStream = context.assets.open(fileName)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
            "{}"
        }
    }
}