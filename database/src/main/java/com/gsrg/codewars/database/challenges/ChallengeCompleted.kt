package com.gsrg.codewars.database.challenges

import androidx.room.Entity

@Entity(tableName = "challengeCompletedTable", primaryKeys = ["username", "challengeId"])
data class ChallengeCompleted(
    val username: String,
    val challengeId: String,
    val challengeName: String?
)