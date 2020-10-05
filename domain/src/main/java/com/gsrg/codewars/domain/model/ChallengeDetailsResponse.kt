package com.gsrg.codewars.domain.model

import com.google.gson.annotations.SerializedName

data class ChallengeDetailsResponse(
    @field:SerializedName("id") val id: String,
    @field:SerializedName("name") val name: String?,
    @field:SerializedName("slug") val slug: String?,
    @field:SerializedName("category") val category: String?,
    @field:SerializedName("languages") val languages: List<String>?,
    @field:SerializedName("url") val url: String?,
    @field:SerializedName("createdBy") val createdBy: CreatedBy?,
    @field:SerializedName("description") val description: String?
)

data class CreatedBy(
    @field:SerializedName("username") val username: String?,
    @field:SerializedName("url") val url: String?
)