package com.gsrg.codewars.domain.data.completed

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.gsrg.codewars.database.CodeWarsDatabase
import com.gsrg.codewars.database.challenges.ChallengeCompleted
import com.gsrg.codewars.database.challenges.CompletedRemoteKeys
import com.gsrg.codewars.domain.api.CodeWarsApiService
import retrofit2.HttpException
import java.io.IOException
import java.io.InvalidObjectException

private const val CHALLENGES_STARTING_PAGE = 0

@ExperimentalPagingApi
class CompletedChallengesMediator(
    private val username: String,
    private val apiService: CodeWarsApiService,
    private val database: CodeWarsDatabase
) : RemoteMediator<Int, ChallengeCompleted>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ChallengeCompleted>): MediatorResult {
        val page: Int = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(username, state)
                remoteKeys?.nextKey?.minus(1) ?: CHALLENGES_STARTING_PAGE
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(username, state)
                if (remoteKeys == null) {
                    throw InvalidObjectException("Remote key and the prevKey should not be null")
                }
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey
                if (prevKey == null) {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                remoteKeys.prevKey!!
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(username, state)
                if (remoteKeys?.nextKey == null) {
                    throw InvalidObjectException("Remote key should not be null for $loadType")
                }
                remoteKeys.nextKey!!
            }
        }
        try {
            val apiResponse = apiService.requestCompletedChallenges(
                username = username,
                page = page
            )
            val challengeList: List<ChallengeCompleted> = apiResponse.challenges.map {
                ChallengeCompleted(
                    username = username,
                    challengeId = it.id,
                    challengeName = it.name
                )
            }
            val endOfPaginationReached = challengeList.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.challengesDao().clearCompletedByUsername(username)
                    database.completedRemoteKeysDao().clearRemoteKeysByUsername(username)
                }
                val prevKey = if (page == CHALLENGES_STARTING_PAGE) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = challengeList.map {
                    CompletedRemoteKeys(
                        username = username,
                        challengeId = it.challengeId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                database.completedRemoteKeysDao().insertAll(keys)
                database.challengesDao().completedByUsername(username)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)

        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(username: String, state: PagingState<Int, ChallengeCompleted>): CompletedRemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position: Int ->
            state.closestItemToPosition(position)?.challengeId?.let { challengeId: String ->
                database.completedRemoteKeysDao().completedRemoteKeys(username = username, challengeId = challengeId)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstItem(username: String, state: PagingState<Int, ChallengeCompleted>): CompletedRemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull {
            it.data.isNotEmpty()
        }?.data?.firstOrNull()?.let { challenge: ChallengeCompleted ->
            // Get the remote keys of the first items retrieved
            database.completedRemoteKeysDao().completedRemoteKeys(username = username, challengeId = challenge.challengeId)
        }
    }

    private suspend fun getRemoteKeyForLastItem(username: String, state: PagingState<Int, ChallengeCompleted>): CompletedRemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull {
            it.data.isNotEmpty()
        }?.data?.lastOrNull()?.let { challenge: ChallengeCompleted ->
            // Get the remote keys of the last item retrieved
            database.completedRemoteKeysDao().completedRemoteKeys(username = username, challengeId = challenge.challengeId)
        }
    }
}