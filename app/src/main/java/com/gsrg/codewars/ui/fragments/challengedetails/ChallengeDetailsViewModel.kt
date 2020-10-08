package com.gsrg.codewars.ui.fragments.challengedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.database.challenges.ChallengeDetails
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.data.challengedetails.IChallengeDetailsRepository
import com.gsrg.codewars.domain.model.ChallengeDetailsResponse
import com.gsrg.codewars.ui.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ChallengeDetailsViewModel
@ViewModelInject constructor(
    private val challengeDetailsRepository: IChallengeDetailsRepository,
    private val database: CodeWarsDatabase
) : ViewModel() {

    private val _challengeRequestLiveData = MutableLiveData<Event<Result<Boolean>>>()
    val challengeRequestLiveData: MutableLiveData<Event<Result<Boolean>>> = _challengeRequestLiveData

    val viewLiveData = MutableLiveData<ChallengeDetails>()

    fun requestChallengeDetails(username: String, challengeId: String) {
        _challengeRequestLiveData.value = Event(Result.Loading)
        viewModelScope.launch {
            requestChallengeDetailsFromDB(username = username, challengeId = challengeId)
            challengeDetailsRepository.getChallengeDetails(challengeId = challengeId).collect { result: Result<ChallengeDetailsResponse> ->
                when (result) {
                    is Result.Success -> {
                        storeRepositoryResponseInDB(username = username, response = result.data)
                        requestChallengeDetailsFromDB(username = username, challengeId = challengeId)
                    }
                    is Result.Error -> {
                        _challengeRequestLiveData.value = Event(result)
                    }
                }
            }
        }
    }

    private suspend fun storeRepositoryResponseInDB(username: String, response: ChallengeDetailsResponse) {
        val challengeDetails = ChallengeDetails(
            playerUsername = username,
            challengeId = response.id,
            name = response.name,
            slug = response.slug,
            category = response.category,
            languages = response.languagesString(),
            url = response.url,
            description = response.description,
            creatorUsername = response.createdBy?.username,
            creatorUrl = response.createdBy?.url
        )
        database.challengeDetailsDao().insertChallengeDetails(challengeDetails = challengeDetails)
    }

    private suspend fun requestChallengeDetailsFromDB(username: String, challengeId: String) {
        val challengeDetailsList: List<ChallengeDetails> = database.challengeDetailsDao().requestChallengeDetailsBy(challengeId = challengeId, playerUsername = username)
        if (challengeDetailsList.isNotEmpty()) {
            _challengeRequestLiveData.value = Event(Result.Success(data = true))
            viewLiveData.value = challengeDetailsList.first()
        }
    }
}