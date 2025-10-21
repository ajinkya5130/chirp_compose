package com.plcoding.core.domain.auth

import kotlinx.serialization.SerialName

data class UserInfo(
    val id: String,
    val email: String,
    val username: String,
    @SerialName("hasVerifiedEmail")
    val isEmailVerified: Boolean,
    val profilePictureUrl: String? = null,

    )
