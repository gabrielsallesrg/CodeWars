package com.gsrg.codewars.domain.data.player

import com.gsrg.codewars.domain.model.Language
import com.gsrg.codewars.domain.model.LanguageRanks
import com.gsrg.codewars.domain.model.PlayerResponse
import io.reactivex.Observable

class PlayerRepositoryMock : IPlayerRepository {

    override fun getPlayerDetailsByName(name: String): Observable<PlayerResponse> {
        return Observable.just(
            PlayerResponse(
                username = "",
                name = null,
                languageRanks = LanguageRanks(
                    overall = Language(0, 0),
                    languages = mapOf(Pair("javascript", Language(0, 0)))
                )
            )
        )
    }
}