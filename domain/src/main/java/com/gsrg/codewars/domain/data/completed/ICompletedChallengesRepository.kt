package com.gsrg.codewars.domain.data.completed

import androidx.paging.PagingData
import com.gsrg.codewars.database.challenges.ChallengeCompleted
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope

interface ICompletedChallengesRepository {

    fun getCompletedChallengesList(username: String, scope: CoroutineScope): Observable<PagingData<ChallengeCompleted>>
}