package com.plcoding.core.domain.auth

import kotlinx.coroutines.flow.Flow

/**
 * Session data storage interface.
 *
 * Defines the contract for persisting and retrieving authentication session data.
 * Implementations should handle secure storage of authentication information.
 */
interface ISessionDataStorage {
    /**
     * Saves authentication information to persistent storage.
     *
     * @param authInfo The authentication information to save, or null to clear the session
     */
    suspend fun saveAuthInfo(authInfo: AuthInfo?)

    /**
     * Retrieves authentication information as a Flow.
     *
     * @return Flow emitting the current [AuthInfo] or null if no session exists
     */
    fun getAuthInfo(): Flow<AuthInfo?>

    /**
     * Clears all stored authentication information.
     */
    suspend fun clearAuthInfo()
}