package com.gsrg.codewars.database.players

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PlayersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(player: Player)

    @Query("SELECT * FROM playerTable")
    fun selectAllPlayers(): List<Player>?

    @Query("DELETE FROM playerTable WHERE date NOT IN (SELECT date FROM playerTable ORDER BY date DESC LIMIT 5)")
    suspend fun keepLast5Players()

    @Query("DELETE FROM playerTable")
    suspend fun clearTable()
}