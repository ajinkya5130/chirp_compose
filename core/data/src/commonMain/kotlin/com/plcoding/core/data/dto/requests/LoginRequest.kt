package com.plcoding.core.data.dto.requests

import kotlinx.serialization.Serializable

/**
 * Request data for user login.
 *
 * This serializable class represents the credentials required to authenticate a user.
 *
 * @property email The user's email address
 * @property password The user's password
 */
@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)
