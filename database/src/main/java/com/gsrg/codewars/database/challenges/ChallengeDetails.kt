package com.gsrg.codewars.database.challenges

import androidx.room.Entity

@Entity(tableName = "challengeDetailsTable", primaryKeys = ["playerUsername", "challengeId"])
data class ChallengeDetails(
    val playerUsername: String,
    val challengeId: String,
    val name: String? = null,
    val slug: String? = null,
    val category: String? = null,
    val languages: String? = null,
    val url: String? = null,
    val description: String? = null,
    val creatorUsername: String? = null,
    val creatorUrl: String? = null
)