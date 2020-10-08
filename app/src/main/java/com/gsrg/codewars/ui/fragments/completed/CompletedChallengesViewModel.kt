package com.gsrg.codewars.ui.fragments.completed

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gsrg.codewars.database.challenges.ChallengeCompleted
import com.gsrg.codewars.domain.data.completed.ICompletedChallengesRepository
import com.gsrg.codewars.domain.utils.TAG
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CompletedChallengesViewModel
@ViewModelInject constructor(
    private val repository: ICompletedChallengesRepository
) : ViewModel() {

    private var disposables = CompositeDisposable()
    private var challengeListObservable: Observable<PagingData<ChallengeCompleted>>? = null
    val challengeListLiveData = MutableLiveData<PagingData<ChallengeCompleted>>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun requestCompletedChallengeListResult(username: String) {
        if (challengeListObservable == null) {

            challengeListObservable = repository.getCompletedChallengesList(username = username, scope = viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

            challengeListObservable!!.subscribe(object : Observer<PagingData<ChallengeCompleted>> {
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(t: PagingData<ChallengeCompleted>) {
                    challengeListLiveData.value = t
                }

                override fun onError(e: Throwable) {
                    Timber.tag(TAG()).e(e)
                }

                override fun onComplete() {
                    Timber.tag(TAG()).d("challengeListObservable: onComplete")
                }
            })
        }
    }
}