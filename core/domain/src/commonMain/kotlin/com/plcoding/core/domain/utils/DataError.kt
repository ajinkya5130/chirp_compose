package com.plcoding.core.domain.utils

/**
 * Sealed interface representing data-related errors.
 *
 * This interface categorizes errors into remote (network/server) and local (device storage) errors.
 */
sealed interface DataError : Error {

    /**
     * Remote errors that occur during network operations or server communication.
     */
    enum class Remote : DataError {
        /** HTTP 400 - Invalid request */
        BAD_REQUEST,

        /** HTTP 401 - Authentication required */
        UNAUTHORIZED,

        /** Request took too long to complete */
        REQUEST_TIMEOUT,

        /** HTTP 403 - Access denied */
        FORBIDDEN,

        /** HTTP 409 - Resource conflict */
        CONFLICT,

        /** HTTP 429 - Rate limit exceeded */
        TOO_MANY_REQUESTS,

        /** No internet connection available */
        NO_INTERNET_CONNECTION,

        /** HTTP 413 - Request payload too large */
        PAYLOAD_TOO_LARGE,

        /** HTTP 404 - Resource not found */
        NOT_FOUND,

        /** Failed to serialize/deserialize data */
        SERIALIZATION_ERROR,

        /** HTTP 500 - Internal server error */
        SERVER_ERROR,

        /** HTTP 503 - Service temporarily unavailable */
        SERVICE_UNAVAILABLE,

        /** Operation timed out */
        TIMEOUT,

        /** Unknown or unhandled error */
        UNKNOWN

    }

    /**
     * Local errors that occur during device storage operations.
     */
    enum class Local : DataError {
        /** Device storage is full */
        DISK_FULL,

        /** Requested file not found */
        FILE_NOT_FOUND,

        /** Unknown or unhandled error */
        UNKNOWN
    }
}