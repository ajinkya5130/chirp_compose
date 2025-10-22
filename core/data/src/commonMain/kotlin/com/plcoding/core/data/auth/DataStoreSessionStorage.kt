package com.plcoding.core.data.auth

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.plcoding.core.data.dto.toSerializableAuth
import com.plcoding.core.domain.auth.AuthInfo
import com.plcoding.core.domain.auth.ISessionDataStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

/**
 * DataStore-based implementation of session storage for authentication information.
 *
 * This class provides persistent storage for user authentication data using AndroidX DataStore.
 * It handles serialization and deserialization of [AuthInfo] objects to/from JSON format.
 *
 * @property dataStore The DataStore instance used for storing preferences
 */
class DataStoreSessionStorage(
    private val dataStore: DataStore<Preferences>,
) : ISessionDataStorage {
    private val authInfoKey = stringPreferencesKey("auth_info_key")

    val json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Saves authentication information to DataStore.
     *
     * If [authInfo] is null, the stored authentication data will be removed from DataStore.
     * Otherwise, the [AuthInfo] object is serialized to JSON and stored persistently.
     *
     * @param authInfo The authentication information to save, or null to clear the stored data
     */
    override suspend fun saveAuthInfo(authInfo: AuthInfo?) {
        if (authInfo == null) {
            dataStore.edit {
                it.remove(authInfoKey)
            }
            return
        }
        val serializedData = json.encodeToString(authInfo.toSerializableAuth())
        dataStore.edit { pref ->
            pref[authInfoKey] = serializedData
        }

    }

    /**
     * Retrieves authentication information from DataStore as a Flow.
     *
     * This function returns a Flow that emits the stored [AuthInfo] whenever the DataStore
     * preferences change. If no authentication data is stored, the Flow emits null.
     * The stored JSON string is deserialized back into an [AuthInfo] object.
     *
     * @return A Flow that emits the current [AuthInfo] or null if no data is stored
     */
    override fun getAuthInfo(): Flow<AuthInfo?> {
        return dataStore.data.map { pref ->
            val serializedJsonData = pref[authInfoKey]
            serializedJsonData?.let {
                json.decodeFromString(it)
            }

        }

    }

    /**
     * Clears all stored authentication information from DataStore.
     *
     * This function removes the authentication data from persistent storage,
     * effectively logging out the user by deleting their session information.
     */
    override suspend fun clearAuthInfo() {
        dataStore.edit {
            it.remove(authInfoKey)
        }
    }

}