package com.gsrg.codewars.domain.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @field:SerializedName("username") val username: String,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("ranks") val languageRanks: LanguageRanks,
) {
    fun usableName(): String = if (name.isNullOrBlank()) username else name
    fun overallRank(): Int = languageRanks.overall.rank
    fun bestLanguage(): String = languageRanks.bestLanguage()
    fun pointsForBestLanguage(): Int = languageRanks.pointsForBestLanguage()

    override fun toString(): String {
        return "PlayerResponse(username='$username', name=$name, languageRanks=$languageRanks)"
    }
}

data class LanguageRanks(
    @field:SerializedName("overall") val overall: Language,
    @field:SerializedName("languages") val languages: Map<String, Language>
) {
    fun bestLanguage(): String {
        var bestLanguageName = ""
        var bestScore = -1
        for ((languageName, languageData) in languages) {
            if (languageData.score > bestScore) {
                bestLanguageName = languageName
                bestScore = languageData.score
            }
        }
        return bestLanguageName
    }

    fun pointsForBestLanguage(): Int {
        var bestScore = -1
        for ((_, languageData) in languages) {
            if (languageData.score > bestScore) {
                bestScore = languageData.score
            }
        }
        return bestScore
    }

    override fun toString(): String {
        return "LanguageRanks(languages=$languages)"
    }
}

data class Language(
    @field:SerializedName("rank") val rank: Int,
    @field:SerializedName("score") val score: Int
) {
    override fun toString(): String {
        return "Language(rank=$rank, score=$score)"
    }
}