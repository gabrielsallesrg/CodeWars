package com.gsrg.codewars.ui.fragments.authored

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import com.gsrg.codewars.domain.authored.IAuthoredChallengesRepository
import com.gsrg.codewars.domain.utils.TAG
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class AuthoredChallengesViewModel
@ViewModelInject constructor(
    private val repository: IAuthoredChallengesRepository
) : ViewModel() {

    private var disposables = CompositeDisposable()
    private var authoredListObservable: Observable<PagingData<AuthoredChallenge>>? = null
    val authoredListLiveData = MutableLiveData<PagingData<AuthoredChallenge>>()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun requestAuthoredChallenges(username: String) {
        if (authoredListObservable == null) {

            authoredListObservable = repository.getAuthoredChallengeList(username = username, scope = viewModelScope)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

            authoredListObservable!!.subscribe(object : Observer<PagingData<AuthoredChallenge>> {
                override fun onSubscribe(d: Disposable) {
                    disposables.add(d)
                }

                override fun onNext(t: PagingData<AuthoredChallenge>) {
                    authoredListLiveData.value = t
                    onComplete()
                }

                override fun onError(e: Throwable) {
                    Timber.tag(TAG()).e(e)
                }

                override fun onComplete() {
                    Timber.tag(TAG()).d("authoredListObservable: onComplete")
                }

            })
        }
    }
}