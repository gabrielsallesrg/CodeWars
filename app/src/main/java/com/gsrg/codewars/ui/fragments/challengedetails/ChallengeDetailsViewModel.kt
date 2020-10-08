package com.gsrg.codewars.ui.fragments.challengedetails

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.database.challenges.ChallengeDetails
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.data.challengedetails.IChallengeDetailsRepository
import com.gsrg.codewars.domain.utils.TAG
import com.gsrg.codewars.ui.Event
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import timber.log.Timber

class ChallengeDetailsViewModel
@ViewModelInject constructor(
    private val challengeDetailsRepository: IChallengeDetailsRepository,
    private val database: CodeWarsDatabase
) : ViewModel() {

    private var disposables = CompositeDisposable()
    private val _challengeRequestLiveData = MutableLiveData<Event<Result<Boolean>>>()
    val challengeRequestLiveData: MutableLiveData<Event<Result<Boolean>>> = _challengeRequestLiveData
    private var challengeObservable: Observable<ChallengeDetails>? = null
    val viewLiveData = MutableLiveData<ChallengeDetails>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun requestChallengeDetails(username: String, challengeId: String) {
        if (challengeObservable == null) {
            _challengeRequestLiveData.value = Event(Result.Loading)
            viewModelScope.launch {
                requestChallengeDetailsFromDB(username = username, challengeId = challengeId)
            }
            challengeObservable = challengeDetailsRepository.getChallengeDetails(challengeId = challengeId)
                .map {
                    ChallengeDetails(
                        playerUsername = username,
                        challengeId = it.id,
                        name = it.name,
                        slug = it.slug,
                        category = it.category,
                        languages = it.languagesString(),
                        url = it.url,
                        description = it.description,
                        creatorUsername = it.createdBy?.username,
                        creatorUrl = it.createdBy?.url
                    )
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

            challengeObservable!!.subscribe(object : Observer<ChallengeDetails> {
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(t: ChallengeDetails) {
                    viewModelScope.launch {
                        storeRepositoryResponseInDB(username = username, challengeDetails = t)
                        requestChallengeDetailsFromDB(username = username, challengeId = challengeId)
                    }
                }

                override fun onError(e: Throwable) {
                    Timber.tag(TAG()).e(e)
                    _challengeRequestLiveData.value = Event(Result.Error(Exception(e), e.message ?: "Something went wrong"))
                }

                override fun onComplete() {
                    Timber.tag(TAG()).d("challengeObservable: onComplete")
                }
            })
        }
    }

    private suspend fun storeRepositoryResponseInDB(username: String, challengeDetails: ChallengeDetails) {
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