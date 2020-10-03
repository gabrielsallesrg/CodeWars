package com.gsrg.codewars.domain.data

import com.gsrg.codewars.domain.api.Result
import com.gsrg.codewars.domain.model.PlayerResponse
import kotlinx.coroutines.flow.Flow

interface IPlayerRepository {

    fun getPlayerDetailsByName(name: String): Flow<Result<PlayerResponse>>
}