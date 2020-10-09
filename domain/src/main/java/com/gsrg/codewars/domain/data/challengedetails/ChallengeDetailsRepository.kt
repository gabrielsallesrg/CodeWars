package com.gsrg.codewars.domain.data.challengedetails

import com.gsrg.codewars.domain.api.CodeWarsApiService
import com.gsrg.codewars.domain.model.ChallengeDetailsResponse
import io.reactivex.Observable
import javax.inject.Inject

class ChallengeDetailsRepository
@Inject constructor(
    private val codeWarsApiService: CodeWarsApiService
) : IChallengeDetailsRepository {

    override fun getChallengeDetails(challengeId: String): Observable<ChallengeDetailsResponse> {
        return codeWarsApiService.requestChallengeDetails(challengeId = challengeId)
    }
}