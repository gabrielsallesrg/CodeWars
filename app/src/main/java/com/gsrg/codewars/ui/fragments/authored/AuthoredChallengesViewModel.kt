package com.gsrg.codewars.ui.fragments.authored

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import com.gsrg.codewars.domain.authored.IAuthoredChallengesRepository
import kotlinx.coroutines.flow.Flow


class AuthoredChallengesViewModel
@ViewModelInject constructor(
    private val repository: IAuthoredChallengesRepository
) : ViewModel() {

    private var authoredListLiveData: Flow<PagingData<AuthoredChallenge>>? = null

    fun requestAuthoredChallenges(username: String): Flow<PagingData<AuthoredChallenge>> {
        if (authoredListLiveData != null) {
            return authoredListLiveData!!
        }
        authoredListLiveData = repository.getAuthoredChallengeList(username = username)

        return authoredListLiveData as Flow<PagingData<AuthoredChallenge>>
    }
}