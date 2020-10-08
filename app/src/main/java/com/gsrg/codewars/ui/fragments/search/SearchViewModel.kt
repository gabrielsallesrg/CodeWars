package com.gsrg.codewars.ui.fragments.search

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.database.players.Player
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.data.player.IPlayerRepository
import com.gsrg.codewars.domain.model.PlayerResponse
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

class SearchViewModel
@ViewModelInject constructor(
    private val playerRepository: IPlayerRepository,
    private val database: CodeWarsDatabase
) : ViewModel() {

    private var disposables = CompositeDisposable()
    private val _playerLiveData = MutableLiveData<Event<Result<PlayerResponse>>>()
    val playerLiveData: MutableLiveData<Event<Result<PlayerResponse>>> = _playerLiveData
    private var playerObservable: Observable<PlayerResponse>? = null

    val playerViewLiveData = MutableLiveData<PlayerResponse>()
    val resultsVisibilityLiveData = MutableLiveData<Int>()
    val last5playersLiveData = MutableLiveData<List<Player>>()
    private var sortByRank: Boolean = false

    init {
        resultsVisibilityLiveData.value = View.GONE
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    fun saveSearchedName() {
        val player = playerViewLiveData.value?.let {
            Player(
                playerUserName = it.username,
                date = System.currentTimeMillis(),
                rank = it.overallRank(),
                bestLanguage = it.bestLanguage(),
                bestLanguageScore = it.pointsForBestLanguage()
            )
        }
        if (player != null) {
            viewModelScope.launch {
                database.playersDao().insert(player)
                database.playersDao().keepLast5Players()
            }
        }
    }

    fun retrieveSavedSearchedNames() {
        viewModelScope.launch {
            val listOfPlayers: List<Player>? = database.playersDao().selectAllPlayers()
            if (listOfPlayers != null) {
                last5playersLiveData.value = sort(listOfPlayers)
            }
        }
    }

    fun sortLast5Players() {
        sortByRank = !sortByRank
        last5playersLiveData.value?.let {
            last5playersLiveData.value = sort(it)
        }
    }

    private fun sort(list: List<Player>): List<Player> {
        return if (sortByRank) {
            list.sortedByDescending { it.rank }
        } else {
            list.sortedByDescending { it.date }
        }
    }

    fun searchForUserByName(name: String) {
        _playerLiveData.value = Event(Result.Loading)
        if (playerObservable != null) {
            disposables.clear()
        }
        playerObservable = playerRepository.getPlayerDetailsByName(name = name)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        playerObservable!!.subscribe(object : Observer<PlayerResponse> {
            override fun onSubscribe(d: Disposable) {
                disposables.add(d)
            }

            override fun onNext(t: PlayerResponse) {
                playerViewLiveData.value = t
                resultsVisibilityLiveData.value = View.VISIBLE
                _playerLiveData.value = Event(Result.Success(data = t))
            }

            override fun onError(e: Throwable) {
                Timber.tag(TAG()).e(e)
                resultsVisibilityLiveData.value = View.GONE
                _playerLiveData.value = Event(Result.Error(Exception(e), e.message ?: "Something went wrong"))
            }

            override fun onComplete() {
                Timber.tag(TAG()).d("playerObservable: onComplete")
            }

        })
    }

    fun hideSearchResult() {
        resultsVisibilityLiveData.value = View.GONE
    }
}