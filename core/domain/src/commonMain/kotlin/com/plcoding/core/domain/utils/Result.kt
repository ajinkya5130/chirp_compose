package com.plcoding.core.domain.utils

/**
 * A type representing either success or failure.
 *
 * This sealed interface provides a type-safe way to handle operations that can succeed or fail.
 *
 * @param D The type of data returned on success
 * @param E The type of error returned on failure, must implement [Error]
 */
sealed interface Result<out D, out E : Error> {
    /**
     * Represents a successful result containing data.
     *
     * @param data The successful result data
     */
    data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Represents a failed result containing an error.
     *
     * @param error The error that caused the failure
     */
    data class Failure<out E : Error>(val error: E) : Result<Nothing, E>
}

/**
 * Transforms the success value of a Result using the provided mapping function.
 *
 * If the Result is a Success, applies the mapping function to the data and returns a new Success.
 * If the Result is a Failure, returns the same Failure unchanged.
 *
 * @param T The type of the original success data
 * @param E The type of error
 * @param R The type of the transformed success data
 * @param map The transformation function to apply to success data
 * @return A new Result with transformed success data or the original failure
 */
inline fun <T, E : Error, R> Result<T, E>.map(map: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(map(data))
        is Result.Failure -> Result.Failure(error)
    }
}

/**
 * Executes the given action if the Result is a Success.
 *
 * @param action The action to execute with the success data
 * @return The original Result unchanged
 */
inline fun <T, E : Error> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Success -> {
            action(data)
            this
        }

        is Result.Failure -> this
    }
}

/**
 * Executes the given action if the Result is a Failure.
 *
 * @param action The action to execute with the error
 * @return The original Result unchanged
 */
inline fun <T, E : Error> Result<T, E>.onFailure(action: (E) -> Unit): Result<T, E> {
    return when (this) {
        is Result.Success -> this
        is Result.Failure -> {
            action(error)
            this
        }
    }
}

/**
 * Type alias for a Result that doesn't return any data on success.
 *
 * Useful for operations that only need to indicate success or failure without returning data.
 */
typealias EmptyResult<E> = Result<Unit, E>

/**
 * Converts a Result to an EmptyResult, discarding any success data.
 *
 * @return An EmptyResult with the same error type
 */
inline fun <T, E : Error> Result<T, E>.asEmptyResult(): EmptyResult<E> {
    return map { }
}

