package com.gsrg.codewars.domain.authored

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import com.gsrg.codewars.domain.api.CodeWarsApiService
import com.gsrg.codewars.domain.utils.TAG
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class AuthoredChallengesRepository
@Inject constructor(
    private val apiService: CodeWarsApiService,
    private val database: CodeWarsDatabase
) : IAuthoredChallengesRepository {

    @ExperimentalPagingApi
    override fun getAuthoredChallengeList(username: String): Flow<PagingData<AuthoredChallenge>> {
        Timber.tag(TAG()).d("username: $username")

        val pagingSourceFactory = { database.authoredChallengeDao().authoredByUsername(username = username) }

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = AuthoredChallengesMediator(
                username = username,
                apiService = apiService,
                database = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    companion object {
        private const val PAGE_SIZE = 50
    }
}