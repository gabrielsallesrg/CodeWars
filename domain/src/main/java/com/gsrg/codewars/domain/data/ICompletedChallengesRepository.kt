package com.gsrg.codewars.domain.data

import androidx.paging.PagingData
import com.gsrg.codewars.domain.model.CompletedChallengesResponse
import kotlinx.coroutines.flow.Flow

interface ICompletedChallengesRepository {

    fun getCompletedChallengesList(username: String, page: Int): Flow<PagingData<CompletedChallengesResponse>>
}