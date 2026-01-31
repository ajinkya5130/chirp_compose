package com.plcoding.core.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin
import org.koin.dsl.module

actual val platformSpecificModule = module {
    single<HttpClientEngine> {
        Darwin.create()
    }
    single<DataStore<Preferences>> {
        createDataStoreForIos()
    }
}