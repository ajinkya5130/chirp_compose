package com.plcoding.core.domain.auth

/**
 * Authentication information container.
 *
 * Contains all the authentication-related data returned after a successful login or token refresh,
 * including access token, refresh token, and user information.
 *
 * @property accessToken The JWT access token used for authenticating API requests
 * @property refreshToken The refresh token used to obtain new access tokens
 * @property user The authenticated user's information
 */
data class AuthInfo(
    val accessToken: String,
    val refreshToken: String,
    val user: UserInfo,
)
