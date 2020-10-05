package com.gsrg.codewars.domain.model

import com.google.gson.annotations.SerializedName

data class ChallengeResume(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String
) {
    override fun toString(): String {
        return "ChallengeResume(id='$id', name='$name')"
    }
}