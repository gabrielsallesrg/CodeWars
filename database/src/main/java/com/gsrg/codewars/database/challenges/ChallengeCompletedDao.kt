package com.gsrg.codewars.database.challenges

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChallengeCompletedDao {

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
}