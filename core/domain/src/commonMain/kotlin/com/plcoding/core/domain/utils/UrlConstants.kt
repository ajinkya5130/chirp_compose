package com.plcoding.core.domain.utils

/**
 * URL and API endpoint constants.
 *
 * Contains all the base URLs, API endpoints, and related constants used throughout the application.
 */
object UrlConstants {
    /** Base URL for API requests */
    const val BASE_URL = "https://chirp.pl-coding.com/api"

    /** Base URL for deep link handling */
    const val BASE_URL_CHIRP = "chirp://chirp.pl-coding.com/api"

    /*---- API Endpoints --- */
    /** Endpoint for email verification */
    const val API_ENDPOINT_VERIFY_EMAIL = "/auth/verify"

    /** Endpoint for resending verification email */
    const val API_ENDPOINT_RESEND_VERIFICATION = "/auth/resend-verification"

    /** Endpoint for user registration */
    const val API_ENDPOINT_REGISTER = "/auth/register"

    /** Endpoint for user login */
    const val API_ENDPOINT_LOGIN = "/auth/login"

    /** Endpoint for refreshing authentication token */
    const val API_ENDPOINT_AUTH_REFRESH_TOKEN = "/auth/refresh"

    /** Endpoint for refreshing authentication token */
    const val API_ENDPOINT_FORGOT_PASSWORD = "/auth/forgot-password"

    /** Endpoint for refreshing authentication token */
    const val API_ENDPOINT_RESET_PASSWORD = "/auth/reset-password"

    /*---- Query Parameter Keys --- */
    /** Query parameter key for token */
    const val KEY_TOKEN = "token"

    /** Auth path prefix with slash */
    const val KEY_AUTH_WITH_SLASH = "auth/"

    /** Network request timeout in milliseconds */
    const val TIMEOUT_VALUE = 20_000L
}