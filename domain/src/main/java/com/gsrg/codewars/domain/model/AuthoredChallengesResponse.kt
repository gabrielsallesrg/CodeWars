package com.gsrg.codewars.domain.model

import com.google.gson.annotations.SerializedName

data class AuthoredChallengesResponse(
    @SerializedName("data") val challenges: List<ChallengeResume> = emptyList()
) {
    override fun toString(): String {
        return "AuthoredChallengesResponse(challenges=$challenges)"
    }
}