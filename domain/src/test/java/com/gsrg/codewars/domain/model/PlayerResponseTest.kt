package com.gsrg.codewars.domain.model

import org.junit.Assert
import org.junit.Test

class PlayerResponseTest {
    @Test
    fun usableNameShouldReturnUsernameWhenNameIsNull() {
        val playerResponse = PlayerResponse(username = "username", name = null, languageRanks = basicLanguageRanks())
        Assert.assertEquals("username", playerResponse.usableName())
    }

    @Test
    fun usableNameShouldReturnUsernameWhenNameIsBlank() {
        val playerResponse = PlayerResponse(username = "username", name = "", languageRanks = basicLanguageRanks())
        Assert.assertEquals("username", playerResponse.usableName())
    }

    @Test
    fun usableNameShouldReturnName() {
        val playerResponse = PlayerResponse(username = "username", name = "name", languageRanks = basicLanguageRanks())
        Assert.assertEquals("name", playerResponse.usableName())
    }

    @Test
    fun shouldReturnBestLanguage() {
        val playerResponse = PlayerResponse(username = "username", name = "name", languageRanks = basicLanguageRanks())
        Assert.assertEquals("Kotlin", playerResponse.bestLanguage())
    }

    @Test
    fun shouldReturnScoreForBestLanguage() {
        val playerResponse = PlayerResponse(username = "username", name = "name", languageRanks = basicLanguageRanks())
        Assert.assertEquals(5000, playerResponse.pointsForBestLanguage())
    }

    @Test
    fun shouldReturnOverallRank() {
        val playerResponse = PlayerResponse(username = "username", name = "name", languageRanks = basicLanguageRanks())
        Assert.assertEquals(5, playerResponse.overallRank())
    }

    private fun basicLanguageRanks(): LanguageRanks {
        return LanguageRanks(
            overall = Language(
                rank = 5,
                score = 600
            ),
            languages = mapOf(
                "Javascript" to Language(rank = 1, score = 100),
                "C#" to Language(rank = -5, score = 300),
                "Kotlin" to Language(rank = 7, score = 5000),
                "Python" to Language(rank = 5, score = 400),
            )
        )
    }
}