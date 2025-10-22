package com.plcoding.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.plcoding.core.data.auth.DATA_STORE_FILE_NAME
import com.plcoding.core.data.auth.createDataStore

fun createDataStoreForAndroid(context: Context): DataStore<Preferences> = createDataStore(
    producePath = { context.filesDir.resolve(DATA_STORE_FILE_NAME).absolutePath }
)