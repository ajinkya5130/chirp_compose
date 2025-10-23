package com.plcoding.core.data.dto.requests

import kotlinx.serialization.Serializable

/**
 * Request data for refreshing authentication token.
 *
 * This serializable class represents the data required to obtain a new access token
 * using a refresh token.
 *
 * @property refreshToken The refresh token to use for obtaining a new access token
 */
@Serializable
data class RefreshTokenRequest(
    val refreshToken: String,
)
