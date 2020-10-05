package com.gsrg.codewars.database.challenges

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CompletedRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CompletedRemoteKeys>)

    @Query("SELECT * FROM completedRemoteKeysTable WHERE username = :username AND challengeId = :challengeId")
    suspend fun completedRemoteKeys(username: String, challengeId: String): CompletedRemoteKeys?

    @Query("DELETE FROM completedRemoteKeysTable WHERE username = :username")
    suspend fun clearRemoteKeysByUsername(username: String)
}