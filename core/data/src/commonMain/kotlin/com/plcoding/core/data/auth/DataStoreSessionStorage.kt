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

class DataStoreSessionStorage(
    private val dataStore: DataStore<Preferences>,
) : ISessionDataStorage {
    private val authInfoKey = stringPreferencesKey("auth_info_key")

    val json = Json {
        ignoreUnknownKeys = true
    }

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

    override fun getAuthInfo(): Flow<AuthInfo?> {
        return dataStore.data.map { pref ->
            val serializedJsonData = pref[authInfoKey]
            serializedJsonData?.let {
                json.decodeFromString(it)
            }

        }

    }

    override suspend fun clearAuthInfo() {
        dataStore.edit {
            it.remove(authInfoKey)
        }
    }

}