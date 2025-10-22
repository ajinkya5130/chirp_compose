package com.plcoding.core.data.logging

import co.touchlab.kermit.Logger
import com.plcoding.core.domain.logging.CustomLogger

/**
 * Application-wide logger implementation using Kermit.
 *
 * This singleton object provides logging functionality across all platforms
 * by delegating to the Kermit logging library. It implements the [CustomLogger]
 * interface to provide a consistent logging API throughout the application.
 */
object AppLogger : CustomLogger {
    /**
     * Logs a debug message.
     *
     * Debug logs are typically used for detailed diagnostic information
     * that is useful during development and troubleshooting.
     *
     * @param message The debug message to log
     */
    override fun debugLog(message: String) {
        Logger.d(message)
    }

    /**
     * Logs an informational message.
     *
     * Info logs are used for general informational messages that highlight
     * the progress of the application at a coarse-grained level.
     *
     * @param message The informational message to log
     */
    override fun infoLog(message: String) {
        Logger.i(message)
    }

    /**
     * Logs a warning message.
     *
     * Warning logs indicate potentially harmful situations or unexpected
     * conditions that don't prevent the application from functioning.
     *
     * @param message The warning message to log
     */
    override fun warnLog(message: String) {
        Logger.w(message)
    }

    /**
     * Logs an error message with optional exception details.
     *
     * Error logs indicate serious problems that have occurred, typically
     * exceptions or critical failures that need immediate attention.
     *
     * @param message The error message to log
     * @param throwable Optional exception/throwable associated with the error
     */
    override fun errorLog(message: String, throwable: Throwable?) {
        Logger.e(message, throwable)
    }
}