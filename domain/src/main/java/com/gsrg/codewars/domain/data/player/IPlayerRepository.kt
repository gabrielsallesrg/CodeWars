package com.gsrg.codewars.domain.data.player

import com.gsrg.codewars.domain.model.PlayerResponse
import io.reactivex.Observable

interface IPlayerRepository {

    fun getPlayerDetailsByName(name: String): Observable<PlayerResponse>
}