package com.gsrg.codewars.domain.authored

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.gsrg.codewars.database.ICodeWarsDatabase
import com.gsrg.codewars.database.challenges.AuthoredChallenge
import com.gsrg.codewars.domain.api.CodeWarsApiService
import retrofit2.HttpException
import java.io.IOException

@ExperimentalPagingApi
class AuthoredChallengesMediator(
    private val username: String,
    private val apiService: CodeWarsApiService,
    private val database: ICodeWarsDatabase
) : RemoteMediator<Int, AuthoredChallenge>() {
    override suspend fun load(loadType: LoadType, state: PagingState<Int, AuthoredChallenge>): MediatorResult {
        if (loadType == LoadType.REFRESH) {
            try {
                val apiResponse = apiService.requestAuthoredChallenges(username)
                val challengeList: List<AuthoredChallenge> = apiResponse.challenges.map {
                    AuthoredChallenge(
                        username = username,
                        challengeId = it.id,
                        challengeName = it.name
                    )
                }
                database.authoredChallengeDao().clearAuthoredByUsername(username)
                database.authoredChallengeDao().insertAllAuthored(challengeList)
                database.authoredChallengeDao().keepAuthoredChallengesFromLast5Players()
                return MediatorResult.Success(endOfPaginationReached = true)

            } catch (exception: IOException) {
                return MediatorResult.Error(exception)
            } catch (exception: HttpException) {
                return MediatorResult.Error(exception)
            }
        }
        return MediatorResult.Success(endOfPaginationReached = false)
    }
}