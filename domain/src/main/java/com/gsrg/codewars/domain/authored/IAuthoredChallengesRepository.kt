package com.gsrg.codewars.domain.authored

import androidx.paging.PagingData
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import kotlinx.coroutines.flow.Flow

interface IAuthoredChallengesRepository {

    fun getAuthoredChallengeList(username: String): Flow<PagingData<AuthoredChallenge>>
}