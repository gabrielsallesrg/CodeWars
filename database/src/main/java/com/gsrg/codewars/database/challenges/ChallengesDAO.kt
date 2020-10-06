package com.gsrg.codewars.database.challenges

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChallengesDAO {

    /**
     * ChallengeCompleted.kt
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCompleted(challengesCompleted: List<ChallengeCompleted>)

    @Query("SELECT * FROM challengeCompletedTable WHERE username LIKE :username")
    fun completedByUsername(username: String): PagingSource<Int, ChallengeCompleted>

    @Query("DELETE FROM challengeCompletedTable WHERE username LIKE :username")
    suspend fun clearCompletedByUsername(username: String)

    @Query("DELETE FROM challengeCompletedTable WHERE username NOT IN(SELECT playerUserName FROM playerTable)")
    suspend fun keepCompletedChallengesFromLast5Players()

    /**
     * AuthoredChallenge.kt
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllAuthored(challengesCompleted: List<AuthoredChallenge>)

    @Query("SELECT * FROM authoredChallengeTable WHERE username LIKE :username")
    fun authoredByUsername(username: String): PagingSource<Int, AuthoredChallenge>

    @Query("DELETE FROM authoredChallengeTable WHERE username LIKE :username")
    suspend fun clearAuthoredByUsername(username: String)

    @Query("DELETE FROM authoredChallengeTable WHERE username NOT IN(SELECT playerUserName FROM playerTable)")
    suspend fun keepAuthoredChallengesFromLast5Players()

    /**
     * ChallengeDetails.kt
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChallengeDetails(challengeDetails: ChallengeDetails)

    @Query("SELECT * FROM challengeDetailsTable WHERE playerUsername LIKE :playerUsername AND challengeId LIKE :challengeId")
    suspend fun requestChallengeDetailsBy(challengeId: String, playerUsername: String): List<ChallengeDetails>

    @Query("DELETE FROM challengeDetailsTable WHERE playerUsername LIKE :playerUsername AND challengeId LIKE :challengeId")
    suspend fun clearChallengeDetailsBy(challengeId: String, playerUsername: String)

    @Query("DELETE FROM challengeDetailsTable WHERE playerUsername NOT IN(SELECT playerUserName FROM playerTable)")
    suspend fun keepChallengeDetailsFromLast5Players()
}