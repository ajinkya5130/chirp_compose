package com.plcoding.core.data.auth

import com.plcoding.core.data.dto.AuthInfoSerializable
import com.plcoding.core.data.dto.requests.LoginRequest
import com.plcoding.core.data.dto.requests.RefreshTokenRequest
import com.plcoding.core.data.dto.requests.RegisterRequest
import com.plcoding.core.data.dto.requests.ResendVerificationEmailRequest
import com.plcoding.core.data.dto.toAuthInfo
import com.plcoding.core.data.networking.get
import com.plcoding.core.data.networking.post
import com.plcoding.core.domain.auth.AuthInfo
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyResult
import com.plcoding.core.domain.utils.Result
import com.plcoding.core.domain.utils.UrlConstants
import com.plcoding.core.domain.utils.map
import io.ktor.client.HttpClient

/**
 * Ktor-based implementation of authentication service.
 *
 * This class provides authentication functionality using Ktor HTTP client for network requests.
 * It handles user login, registration, email verification, and related authentication operations.
 *
 * @property httpClient The Ktor HTTP client used for making network requests
 */
class KtorAuthService(private val httpClient: HttpClient) : IAuthService {

    /**
     * Authenticates a user with email and password.
     *
     * Sends a login request to the server and returns authentication information
     * including access token, refresh token, and user details upon successful authentication.
     *
     * @param email The user's email address
     * @param password The user's password
     * @return Result containing [AuthInfo] on success or [DataError.Remote] on failure
     */
    override suspend fun login(
        email: String,
        password: String,
    ): Result<AuthInfo, DataError.Remote> {
        return httpClient.post<LoginRequest, AuthInfoSerializable>(
            route = UrlConstants.API_ENDPOINT_LOGIN,
            body = LoginRequest(email, password)
        ).map { authInfoSerializable ->
            authInfoSerializable.toAuthInfo()
        }
    }

    /**
     * Registers a new user account.
     *
     * Creates a new user account with the provided credentials. The user will typically
     * need to verify their email address before being able to log in.
     *
     * @param email The user's email address
     * @param password The user's password
     * @param username The desired username for the account
     * @return EmptyResult indicating success or [DataError.Remote] on failure
     */
    override suspend fun register(
        email: String,
        password: String,
        username: String,
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = UrlConstants.API_ENDPOINT_REGISTER,
            body = RegisterRequest(email, password, username)
        )
    }

    /**
     * Resends the email verification link to the user.
     *
     * Triggers the server to send a new verification email to the specified email address.
     * This is useful when the original verification email was not received or has expired.
     *
     * @param email The email address to send the verification link to
     * @return EmptyResult indicating success or [DataError.Remote] on failure
     */
    override suspend fun resendVerificationEmail(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = UrlConstants.API_ENDPOINT_RESEND_VERIFICATION,
            body = ResendVerificationEmailRequest(email)
        )
    }

    /**
     * Verifies a user's email address using a verification token.
     *
     * Validates the email verification token sent to the user's email address.
     * Upon successful verification, the user's email will be marked as verified.
     *
     * @param token The verification token from the email link
     * @return EmptyResult indicating success or [DataError.Remote] on failure
     */
    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> {
        return httpClient.get(
            route = UrlConstants.API_ENDPOINT_VERIFY_EMAIL,
            queryParams = mapOf(UrlConstants.KEY_TOKEN to token)
        )
    }

    /**
     * Refreshes the authentication token using a refresh token.
     *
     * Sends a request to the server with the refresh token to obtain new access and refresh tokens.
     * This is typically called when the access token has expired.
     *
     * @param refreshToken The refresh token to use for obtaining new tokens
     * @return Result containing new [AuthInfo] on success or [DataError.Remote] on failure
     */
    override suspend fun refreshAuthToken(refreshToken: String): Result<AuthInfo, DataError.Remote> {
        return httpClient.post(
            route = UrlConstants.API_ENDPOINT_AUTH_REFRESH_TOKEN,
            body = RefreshTokenRequest(refreshToken)
        )
    }

    override suspend fun forgotPassword(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = UrlConstants.API_ENDPOINT_FORGOT_PASSWORD,
            body = ResendVerificationEmailRequest(email)
        )
    }


}