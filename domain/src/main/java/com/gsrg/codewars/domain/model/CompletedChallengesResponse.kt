package com.gsrg.codewars.domain.model

import com.google.gson.annotations.SerializedName

data class CompletedChallengesResponse(
    @SerializedName("totalPages") val totalPages: Int = 0,
    @SerializedName("totalItems") val totalItems: Int = 0,
    @SerializedName("data") val challenges: List<ChallengeResume> = emptyList(),
    val nextPage: Int? = null
) {
    override fun toString(): String {
        return "CompletedChallengesResponse(totalPages=$totalPages, totalItems=$totalItems, challenges=$challenges)"
    }
}