package com.gsrg.codewars.domain.api

import com.gsrg.codewars.domain.model.AuthoredChallengesResponse
import com.gsrg.codewars.domain.model.ChallengeDetailsResponse
import com.gsrg.codewars.domain.model.CompletedChallengesResponse
import com.gsrg.codewars.domain.model.PlayerResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


/**
 * CodeWars API communication via Retrofit
 */
interface CodeWarsApiService {
    /**
     * Find user whose username or id matches the query
     */
    @GET("users/{id_or_username}")
    fun searchPlayer(
        @Path("id_or_username") idOrUsername: String
    ): Observable<PlayerResponse>

    /**
     * Request completed challenges for user
     */
    @GET("users/{username}/code-challenges/completed")
    suspend fun requestCompletedChallenges(
        @Path("username") username: String,
        @Query("page") page: Int
    ): CompletedChallengesResponse

    /**
     * Request authored challenges by user
     */
    @GET("users/{username}/code-challenges/authored")
    suspend fun requestAuthoredChallenges(
        @Path("username") username: String
    ): AuthoredChallengesResponse

    /**
     * Request challenge details by challengeId
     */
    @GET("code-challenges/{challengeId}")
    fun requestChallengeDetails(
        @Path("challengeId") challengeId: String
    ): Observable<ChallengeDetailsResponse>
}