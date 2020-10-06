package com.gsrg.codewars.domain.authored

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import com.gsrg.codewars.domain.api.CodeWarsApiService

@ExperimentalPagingApi
class AuthoredChallengesMediator(
    private val username: String,
    private val apiService: CodeWarsApiService,
    private val database: CodeWarsDatabase
) : RemoteMediator<Int, AuthoredChallenge>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, AuthoredChallenge>): MediatorResult {
        //TODO("Not yet implemented")
        return MediatorResult.Success(endOfPaginationReached = true)
    }
}