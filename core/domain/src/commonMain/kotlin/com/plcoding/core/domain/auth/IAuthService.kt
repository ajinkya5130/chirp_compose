package com.plcoding.core.domain.auth

import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.EmptyResult

interface IAuthService {
    suspend fun register(
        email: String,
        password: String,
        username: String,
    ): EmptyResult<DataError.Remote>
}