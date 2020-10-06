package com.gsrg.codewars.ui.fragments.authored

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import kotlinx.coroutines.flow.Flow


class AuthoredChallengesViewModel
@ViewModelInject constructor(
    
) : ViewModel() {

    private var authoredListLiveData: Flow<PagingData<AuthoredChallenge>>? = null

    fun requestAuthoredChallenges(username: String): Flow<PagingData<AuthoredChallenge>> {
        if (authoredListLiveData != null) {
            return authoredListLiveData!!
        }
        //TODO authoredListLiveData = repository.getAuthoredChallengeList(username = username)

        return authoredListLiveData as Flow<PagingData<AuthoredChallenge>>
    }
}