package com.plcoding.core.data.auth

import com.plcoding.core.data.dto.requests.RegisterRequest
import com.plcoding.core.data.networking.constructRoute
import com.plcoding.core.data.networking.post
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyResult
import io.ktor.client.HttpClient

class KtorAuthService(private val httpClient: HttpClient) : IAuthService {
    override suspend fun register(
        email: String,
        password: String,
        username: String,
    ): EmptyResult<DataError.Remote> {
        return httpClient.post(
            route = constructRoute("/auth/register"),
            body = RegisterRequest(email, password, username)
        )


    }
}