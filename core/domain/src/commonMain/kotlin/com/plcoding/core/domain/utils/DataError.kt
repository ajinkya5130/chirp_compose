package com.plcoding.core.domain.utils

sealed interface DataError : Error {

    enum class Remote : DataError {
        BAD_REQUEST,
        UNAUTHORIZED,
        REQUEST_TIMEOUT,
        FORBIDDEN,
        CONFLICT,
        TOO_MANY_REQUESTS,
        NO_INTERNET_CONNECTION,
        PAYLOAD_TOO_LARGE,
        NOT_FOUND,
        SERIALIZATION_ERROR,
        SERVER_ERROR,
        SERVICE_UNAVAILABLE,
        TIMEOUT,
        UNKNOWN

    }

    enum class Local : DataError {
        DISK_FULL,
        FILE_NOT_FOUND,
        UNKNOWN
    }
}