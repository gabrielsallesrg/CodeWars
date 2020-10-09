package com.gsrg.codewars.domain.data.challengedetails

import com.gsrg.codewars.domain.model.ChallengeDetailsResponse
import io.reactivex.Observable

interface IChallengeDetailsRepository {

    fun getChallengeDetails(challengeId: String): Observable<ChallengeDetailsResponse>
}