package com.plcoding.core.data.auth

import com.plcoding.core.data.dto.requests.RegisterRequest
import com.plcoding.core.data.dto.requests.ResendVerificationEmailRequest
import com.plcoding.core.data.networking.get
import com.plcoding.core.data.networking.post
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyResult
import com.plcoding.core.domain.utils.UrlConstants
import com.plcoding.core.domain.utils.UrlConstants.KEY_TOKEN
import io.ktor.client.HttpClient

class KtorAuthService(private val httpClient: HttpClient) : IAuthService {
    override suspend fun register(
        email: String,
        password: String,
        username: String,
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/register",
            body = RegisterRequest(email, password, username)
        )
    }

    override suspend fun resendVerificationEmail(email: String): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = "/auth/resend-verification",
            body = ResendVerificationEmailRequest(email)
        )
    }

    override suspend fun verifyEmail(token: String): EmptyResult<DataError.Remote> {
        return httpClient.get(
            route = UrlConstants.API_ENDPOINT_VERIFY_EMAIL,
            queryParams = mapOf(KEY_TOKEN to token)
        )
    }
}