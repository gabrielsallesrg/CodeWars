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
import com.gsrg.codewars.ui.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel
@ViewModelInject constructor(
    private val playerRepository: IPlayerRepository,
    private val database: CodeWarsDatabase
) : ViewModel() {

    private val _playerLiveData = MutableLiveData<Event<Result<PlayerResponse>>>()
    val playerLiveData: MutableLiveData<Event<Result<PlayerResponse>>> = _playerLiveData

    val playerViewLiveData = MutableLiveData<PlayerResponse>()
    val resultsVisibilityLiveData = MutableLiveData<Int>()
    val last5playersLiveData = MutableLiveData<List<Player>>()
    private var sortByRank: Boolean = false

    init {
        resultsVisibilityLiveData.value = View.GONE
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
        viewModelScope.launch {
            playerRepository.getPlayerDetailsByName(name = name)
                .collect { result: Result<PlayerResponse> ->
                    when (result) {
                        is Result.Success -> {
                            playerViewLiveData.value = result.data
                            resultsVisibilityLiveData.value = View.VISIBLE
                            _playerLiveData.value = Event(Result.Success(data = result.data))
                        }
                        is Result.Error -> {
                            resultsVisibilityLiveData.value = View.GONE
                            _playerLiveData.value = Event(result)
                        }
                    }
                }
        }
    }

    fun hideSearchResult() {
        resultsVisibilityLiveData.value = View.GONE
    }
}