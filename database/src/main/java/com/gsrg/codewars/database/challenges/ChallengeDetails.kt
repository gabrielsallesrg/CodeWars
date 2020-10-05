package com.gsrg.codewars.database.challenges

import androidx.room.Entity

@Entity(tableName = "challengeDetailsTable", primaryKeys = ["playerUsername", "challengeId"])
data class ChallengeDetails(
    val playerUsername: String,
    val challengeId: String,
    val name: String?,
    val slug: String?,
    val category: String?,
    val languages: List<String>?,
    val url: String?,
    val description: String?,
    val creatorUsername: String?,
    val creatorUrl: String?
)