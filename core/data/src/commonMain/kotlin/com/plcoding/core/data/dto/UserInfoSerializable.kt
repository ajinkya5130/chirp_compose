package com.plcoding.core.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Serializable data transfer object for user information.
 *
 * This class represents user data that can be serialized to/from JSON
 * for network transmission or local storage. It contains all essential user details.
 *
 * @property id The unique identifier for the user
 * @property email The user's email address
 * @property username The user's username
 * @property isEmailVerified Whether the user's email has been verified (mapped from "hasVerifiedEmail" in JSON)
 * @property profilePictureUrl Optional URL to the user's profile picture
 */
@Serializable
data class UserInfoSerializable(
    val id: String,
    val email: String,
    val username: String,
    @SerialName("hasVerifiedEmail")
    val isEmailVerified: Boolean,
    val profilePictureUrl: String? = null,
)