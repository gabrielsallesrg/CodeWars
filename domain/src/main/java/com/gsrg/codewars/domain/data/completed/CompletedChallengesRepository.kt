package com.gsrg.codewars.domain.data.completed

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.observable
import com.gsrg.codewars.database.ICodeWarsDatabase
import com.gsrg.codewars.database.challenges.ChallengeCompleted
import com.gsrg.codewars.domain.api.CodeWarsApiService
import com.gsrg.codewars.domain.utils.TAG
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope
import timber.log.Timber
import javax.inject.Inject

class CompletedChallengesRepository
@Inject constructor(
    private val apiService: CodeWarsApiService,
    private val database: ICodeWarsDatabase
) : ICompletedChallengesRepository {

    @ExperimentalPagingApi
    override fun getCompletedChallengesList(username: String, scope: CoroutineScope): Observable<PagingData<ChallengeCompleted>> {
        Timber.tag(TAG()).d("username: $username")

        val pagingSourceFactory = { database.challengeCompletedDao().completedByUsername(username = username) }

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            remoteMediator = CompletedChallengesMediator(
                username = username,
                apiService = apiService,
                database = database
            ),
            pagingSourceFactory = pagingSourceFactory
        ).observable
            .cachedIn(scope)
    }

    companion object {
        private const val NETWORK_PAGE_SIZE = 200
    }
}