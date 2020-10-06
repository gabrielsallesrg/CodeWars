package com.gsrg.codewars.database.challenges

import androidx.room.Entity

@Entity(tableName = "authoredChallengeTable", primaryKeys = ["username", "challengeId"])
data class AuthoredChallenge(
    val username: String,
    val challengeId: String,
    val challengeName: String?
)