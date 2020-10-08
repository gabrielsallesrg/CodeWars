package com.gsrg.codewars.database.challenges

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthoredChallengeDao {

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
}