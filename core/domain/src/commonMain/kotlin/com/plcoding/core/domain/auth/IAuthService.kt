package com.plcoding.core.domain.auth

import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyResult
import com.plcoding.core.domain.utils.Result

/**
 * Authentication service interface.
 *
 * Defines the contract for authentication operations including user login, registration,
 * email verification, and token refresh functionality.
 */
interface IAuthService {

    /**
     * Authenticates a user with email and password.
     *
     * @param email The user's email address
     * @param password The user's password
     * @return Result containing [AuthInfo] on success or [DataError.Remote] on failure
     */
    suspend fun login(
        email: String,
        password: String,
    ): Result<AuthInfo, DataError.Remote>

    /**
     * Registers a new user account.
     *
     * @param email The user's email address
     * @param password The user's password
     * @param username The desired username for the account
     * @return EmptyResult indicating success or [DataError.Remote] on failure
     */
    suspend fun register(
        email: String,
        password: String,
        username: String,
    ): EmptyResult<DataError.Remote>

    /**
     * Resends the email verification link to the user.
     *
     * @param email The email address to send the verification link to
     * @return EmptyResult indicating success or [DataError.Remote] on failure
     */
    suspend fun resendVerificationEmail(
        email: String,
    ): EmptyResult<DataError.Remote>

    /**
     * Verifies a user's email address using a verification token.
     *
     * @param token The verification token from the email link
     * @return EmptyResult indicating success or [DataError.Remote] on failure
     */
    suspend fun verifyEmail(
        token: String,
    ): EmptyResult<DataError.Remote>

    /**
     * Refreshes the authentication token using a refresh token.
     *
     * @param refreshToken The refresh token to use for obtaining a new access token
     * @return Result containing new [AuthInfo] on success or [DataError.Remote] on failure
     */
    suspend fun refreshAuthToken(
        refreshToken: String,
    ): Result<AuthInfo, DataError.Remote>


    suspend fun forgotPassword(
        email: String,
    ): EmptyResult<DataError.Remote>


}