package com.plcoding.core.domain.auth

import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyResult

interface IAuthService {
    suspend fun register(
        email: String,
        password: String,
        username: String,
    ): EmptyResult<DataError.Remote>

    suspend fun resendVerificationEmail(
        email: String,
    ): EmptyResult<DataError.Remote>

    suspend fun verifyEmail(
        token: String,
    ): EmptyResult<DataError.Remote>
}