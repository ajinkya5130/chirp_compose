package com.plcoding.core.domain.auth

import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyResult
import com.plcoding.core.domain.utils.Result

interface IAuthService {

    suspend fun login(
        email: String,
        password: String,
    ): Result<AuthInfo, DataError.Remote>

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