package com.plcoding.core.data.dto

import kotlinx.serialization.Serializable

/**
 * Serializable data transfer object for authentication information.
 *
 * This class represents authentication data that can be serialized to/from JSON
 * for network transmission or local storage. It contains access and refresh tokens
 * along with user information.
 *
 * @property accessToken The JWT access token for authenticating API requests
 * @property refreshToken The JWT refresh token for obtaining new access tokens
 * @property user The serializable user information
 */
@Serializable
data class AuthInfoSerializable(
    val accessToken: String,
    val refreshToken: String,
    val user: UserInfoSerializable,
)
