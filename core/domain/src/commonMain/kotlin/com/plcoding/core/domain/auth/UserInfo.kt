package com.plcoding.core.domain.auth

import kotlinx.serialization.SerialName

/**
 * User information data class.
 *
 * Contains the authenticated user's profile information.
 *
 * @property id The unique identifier for the user
 * @property email The user's email address
 * @property username The user's username
 * @property isEmailVerified Whether the user's email has been verified
 * @property profilePictureUrl Optional URL to the user's profile picture
 */
data class UserInfo(
    val id: String,
    val email: String,
    val username: String,
    @SerialName("hasVerifiedEmail")
    val isEmailVerified: Boolean,
    val profilePictureUrl: String? = null,

    )
