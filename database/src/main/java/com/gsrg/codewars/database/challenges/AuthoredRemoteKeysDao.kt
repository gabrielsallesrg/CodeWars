package com.gsrg.codewars.database.challenges

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthoredRemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<AuthoredRemoteKeys>)

    @Query("SELECT * FROM authoredRemoteKeysTable WHERE username = :username AND challengeId = :challengeId")
    suspend fun completedRemoteKeys(username: String, challengeId: String): AuthoredRemoteKeys?

    @Query("DELETE FROM authoredRemoteKeysTable WHERE username = :username")
    suspend fun clearRemoteKeysByUsername(username: String)

    @Query("DELETE FROM authoredRemoteKeysTable WHERE username NOT IN(SELECT playerUserName FROM playerTable)")
    suspend fun keepRemoteKeysFromLast5Players()
}