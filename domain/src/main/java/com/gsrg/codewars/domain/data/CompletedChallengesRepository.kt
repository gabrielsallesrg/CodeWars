package com.gsrg.codewars.domain.data

import androidx.paging.PagingData
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.domain.api.CodeWarsApiService
import com.gsrg.codewars.domain.model.CompletedChallengesResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CompletedChallengesRepository
@Inject constructor(
    private val apiService: CodeWarsApiService,
    private val database: CodeWarsDatabase
) : ICompletedChallengesRepository {

    override fun getCompletedChallengesList(username: String, page: Int): Flow<PagingData<CompletedChallengesResponse>> {
        TODO("Not yet implemented")
    }
}