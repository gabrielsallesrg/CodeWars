package com.gsrg.codewars.database.players

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "playerTable")
data class Player(
    @PrimaryKey
    val playerUserName: String,
    val date: Long
)