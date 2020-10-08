package com.gsrg.codewars.domain.data.challengedetails

import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.model.ChallengeDetailsResponse
import kotlinx.coroutines.flow.Flow

interface IChallengeDetailsRepository {

    fun getChallengeDetails(challengeId: String): Flow<Result<ChallengeDetailsResponse>>
}