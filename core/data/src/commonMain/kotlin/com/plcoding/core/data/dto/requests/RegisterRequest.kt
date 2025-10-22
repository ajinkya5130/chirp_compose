package com.plcoding.core.data.dto.requests

import kotlinx.serialization.Serializable

/**
 * Request data for user registration.
 *
 * This serializable class represents the data required to register a new user account.
 *
 * @property email The email address for the new account
 * @property password The password for the new account
 * @property username The desired username for the new account
 */
@Serializable
data class RegisterRequest(
    val email: String,
    val password: String,
    val username: String,
)
