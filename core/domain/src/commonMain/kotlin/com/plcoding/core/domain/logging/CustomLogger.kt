package com.plcoding.core.domain.logging

/**
 * Custom logger interface for application-wide logging.
 *
 * Provides a platform-agnostic logging interface with different log levels.
 * Implementations should handle platform-specific logging mechanisms.
 */
interface CustomLogger {
    /**
     * Logs a debug message.
     *
     * @param message The debug message to log
     */
    fun debugLog(message: String)

    /**
     * Logs an informational message.
     *
     * @param message The informational message to log
     */
    fun infoLog(message: String)

    /**
     * Logs a warning message.
     *
     * @param message The warning message to log
     */
    fun warnLog(message: String)

    /**
     * Logs an error message with an optional throwable.
     *
     * @param message The error message to log
     * @param throwable Optional throwable/exception associated with the error
     */
    fun errorLog(message: String, throwable: Throwable? = null)
}