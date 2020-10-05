package com.gsrg.codewars.database.challenges

import androidx.room.Entity

@Entity(tableName = "completedRemoteKeysTable", primaryKeys = ["username", "challengeId"])
data class CompletedRemoteKeys(
    val username: String,
    val challengeId: String,
    val prevKey: Int?,
    val nextKey: Int?
)