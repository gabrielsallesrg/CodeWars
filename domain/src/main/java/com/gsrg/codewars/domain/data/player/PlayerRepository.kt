package com.gsrg.codewars.domain.data.player

import com.gsrg.codewars.domain.api.CodeWarsApiService
import com.gsrg.codewars.domain.model.PlayerResponse
import io.reactivex.Observable
import javax.inject.Inject

class PlayerRepository
@Inject constructor(private val codeWarsApiService: CodeWarsApiService) : IPlayerRepository {

    override fun getPlayerDetailsByName(name: String): Observable<PlayerResponse> {
        return codeWarsApiService.searchPlayer(idOrUsername = name)
    }
}