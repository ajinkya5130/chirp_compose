package com.plcoding.core.data.dto.requests

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResetPassword(
    val token: String,
    @SerialName("newPassword")
    val password: String,
)
