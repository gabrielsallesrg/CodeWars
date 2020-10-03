package com.gsrg.codewars.domain.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @field:SerializedName("success") val success: Boolean?,
    @field:SerializedName("reason") val reason: String?
)