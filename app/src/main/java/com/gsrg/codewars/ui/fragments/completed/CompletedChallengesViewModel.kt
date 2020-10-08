package com.gsrg.codewars.ui.fragments.completed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.gsrg.codewars.database.challenges.ChallengeCompleted
import com.gsrg.codewars.domain.data.completed.ICompletedChallengesRepository
import kotlinx.coroutines.flow.Flow

class CompletedChallengesViewModel
@ViewModelInject constructor(
    private val repository: ICompletedChallengesRepository
) : ViewModel() {

    private var challengeListResult: Flow<PagingData<ChallengeCompleted>>? = null

    fun requestCompletedChallengeListResult(username: String): Flow<PagingData<ChallengeCompleted>> {
        if (challengeListResult != null) {
            return challengeListResult!!
        }
        challengeListResult = repository.getCompletedChallengesList(username = username)

        return challengeListResult as Flow<PagingData<ChallengeCompleted>>
    }
}