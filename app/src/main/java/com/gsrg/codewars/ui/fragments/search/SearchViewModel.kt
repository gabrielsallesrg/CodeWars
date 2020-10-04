package com.gsrg.codewars.ui.fragments.search

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.data.IPlayerRepository
import com.gsrg.codewars.domain.model.PlayerResponse
import com.gsrg.codewars.ui.Event
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SearchViewModel
@ViewModelInject constructor(
    private val playerRepository: IPlayerRepository
) : ViewModel() {

    private val _playerLiveData = MutableLiveData<Event<Result<PlayerResponse>>>()
    val playerLiveData: MutableLiveData<Event<Result<PlayerResponse>>> = _playerLiveData

    val playerViewLiveData = MutableLiveData<PlayerResponse>()
    val resultsVisibilityLiveData = MutableLiveData<Int>()

    init {
        resultsVisibilityLiveData.value = View.GONE
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
}