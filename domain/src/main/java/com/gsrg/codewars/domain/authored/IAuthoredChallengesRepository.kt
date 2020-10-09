package com.gsrg.codewars.domain.authored

import androidx.paging.PagingData
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import io.reactivex.Observable
import kotlinx.coroutines.CoroutineScope

interface IAuthoredChallengesRepository {

    fun getAuthoredChallengeList(username: String, scope: CoroutineScope): Observable<PagingData<AuthoredChallenge>>
}