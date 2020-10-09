package com.gsrg.codewars.database

import androidx.paging.PagingSource
import com.gsrg.codewars.database.challenges.*
import com.gsrg.codewars.database.players.Player
import com.gsrg.codewars.database.players.PlayersDao

class CodeWarsDatabaseMock : ICodeWarsDatabase {
    override fun playersDao(): PlayersDao {
        return object : PlayersDao {
            override suspend fun insert(player: Player) {}
            override suspend fun selectAllPlayers(): List<Player>? = null
            override suspend fun keepLast5Players() {}
            override suspend fun clearTable() {}
        }
    }

    override fun challengeCompletedDao(): ChallengeCompletedDao {
        return object : ChallengeCompletedDao {
            override suspend fun insertAllCompleted(challengesCompleted: List<ChallengeCompleted>) {}
            override fun completedByUsername(username: String): PagingSource<Int, ChallengeCompleted> {
                return object : PagingSource<Int, ChallengeCompleted>() {
                    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ChallengeCompleted> {
                        return LoadResult.Page(data = emptyList(), nextKey = null, prevKey = null)
                    }
                }
            }

            override suspend fun clearCompletedByUsername(username: String) {}
            override suspend fun keepCompletedChallengesFromLast5Players() {}
        }
    }

    override fun completedRemoteKeysDao(): CompletedRemoteKeysDao {
        return object : CompletedRemoteKeysDao {
            override suspend fun insertAll(remoteKey: List<CompletedRemoteKeys>) {}
            override suspend fun completedRemoteKeys(username: String, challengeId: String): CompletedRemoteKeys? = null
            override suspend fun clearRemoteKeysByUsername(username: String) {}
            override suspend fun keepRemoteKeysFromLast5Players() {}
        }
    }

    override fun authoredChallengeDao(): AuthoredChallengeDao {
        return object : AuthoredChallengeDao {
            override suspend fun insertAllAuthored(challengesCompleted: List<AuthoredChallenge>) {}
            override fun authoredByUsername(username: String): PagingSource<Int, AuthoredChallenge> {
                return object : PagingSource<Int, AuthoredChallenge>() {
                    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, AuthoredChallenge> {
                        return LoadResult.Page(data = emptyList(), nextKey = null, prevKey = null)
                    }
                }
            }

            override suspend fun clearAuthoredByUsername(username: String) {}
            override suspend fun keepAuthoredChallengesFromLast5Players() {}
        }
    }

    override fun challengeDetailsDao(): ChallengeDetailsDao {
        return object : ChallengeDetailsDao {
            override suspend fun insertChallengeDetails(challengeDetails: ChallengeDetails) {}
            override suspend fun requestChallengeDetailsBy(challengeId: String, playerUsername: String): List<ChallengeDetails> = emptyList()
            override suspend fun clearChallengeDetailsBy(challengeId: String, playerUsername: String) {}
            override suspend fun keepChallengeDetailsFromLast5Players() {}
        }
    }
}