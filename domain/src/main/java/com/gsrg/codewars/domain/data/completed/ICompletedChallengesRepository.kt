package com.gsrg.codewars.domain.data.completed

import androidx.paging.PagingData
import com.gsrg.codewars.database.challenges.ChallengeCompleted
import kotlinx.coroutines.flow.Flow

interface ICompletedChallengesRepository {

    fun getCompletedChallengesList(username: String): Flow<PagingData<ChallengeCompleted>>
}