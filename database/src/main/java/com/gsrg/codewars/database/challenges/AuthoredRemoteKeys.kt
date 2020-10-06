package com.gsrg.codewars.database.challenges

import androidx.room.Entity

@Entity(tableName = "authoredRemoteKeysTable", primaryKeys = ["username", "challengeId"])
data class AuthoredRemoteKeys(
    val username: String,
    val challengeId: String,
    val prevKey: Int?,
    val nextKey: Int?
)