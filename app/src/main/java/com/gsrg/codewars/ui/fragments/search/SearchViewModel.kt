package com.gsrg.codewars.ui.fragments.search

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

    fun searchForUserByName(name: String) {
        _playerLiveData.value = Event(Result.Loading)
        viewModelScope.launch {
            playerRepository.getPlayerDetailsByName(name = name)
                .collect { result: Result<PlayerResponse> ->
                    when (result) {
                        is Result.Success -> _playerLiveData.value =
                            Event(Result.Success(data = result.data))
                        is Result.Error -> _playerLiveData.value = Event(result)
                    }
                }
        }
    }
}