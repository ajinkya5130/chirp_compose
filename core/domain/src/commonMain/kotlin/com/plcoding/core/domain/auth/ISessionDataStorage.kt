package com.plcoding.core.domain.auth

import kotlinx.coroutines.flow.Flow

interface ISessionDataStorage {
    suspend fun saveAuthInfo(authInfo: AuthInfo?)
    fun getAuthInfo(): Flow<AuthInfo?>
    suspend fun clearAuthInfo()
}