package com.gsrg.codewars.database.challenges

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ChallengeDetailsDao {

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