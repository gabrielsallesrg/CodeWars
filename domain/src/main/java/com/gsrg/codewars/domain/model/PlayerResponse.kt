package com.gsrg.codewars.domain.model

import com.google.gson.annotations.SerializedName

data class PlayerResponse(
    @field:SerializedName("username") val username: String,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("ranks") val languageRanks: LanguageRanks,
) {
    override fun toString(): String {
        return "PlayerResponse(username='$username', name=$name, languageRanks=$languageRanks)"
    }
}

data class LanguageRanks(
    @field:SerializedName("languages") val languages: Map<String, Language>
) {
    override fun toString(): String {
        return "LanguageRanks(languages=$languages)"
    }
}

data class Language(
    @field:SerializedName("rank") val rank: Int
) {
    override fun toString(): String {
        return "Language(rank=$rank)"
    }
}