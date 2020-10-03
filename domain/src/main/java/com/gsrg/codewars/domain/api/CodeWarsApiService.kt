package com.gsrg.codewars.domain.api

import com.gsrg.codewars.domain.model.PlayerResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


/**
 * CodeWars API communication via Retrofit
 */
interface CodeWarsApiService {
    /**
     * Find user whose username or id matches the query
     */
    @GET("users/{id_or_username}")
    suspend fun searchPlayer(
        @Path("id_or_username") idOrUsername: String
    ): Response<PlayerResponse>
}