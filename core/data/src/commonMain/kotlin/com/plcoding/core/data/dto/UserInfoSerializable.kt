package com.plcoding.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserInfoSerializable(
    val id: String,
    val email: String,
    val username: String,
    @SerialName("hasVerifiedEmail")
    val isEmailVerified: Boolean,
    val profilePictureUrl: String? = null,
)