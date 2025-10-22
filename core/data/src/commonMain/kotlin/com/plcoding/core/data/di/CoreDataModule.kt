package com.plcoding.core.data.di

import com.plcoding.core.data.auth.DataStoreSessionStorage
import com.plcoding.core.data.auth.KtorAuthService
import com.plcoding.core.data.logging.AppLogger
import com.plcoding.core.data.networking.HttpClientFactory
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.auth.ISessionDataStorage
import com.plcoding.core.domain.logging.CustomLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Platform-specific dependency injection module.
 *
 * This module must be implemented by each platform (Android, iOS, etc.) to provide
 * platform-specific dependencies such as DataStore and HTTP client engine.
 */
expect val platformSpecificModule: Module

/**
 * Core data layer dependency injection module.
 *
 * This module provides all the core data layer dependencies including:
 * - HTTP client for network requests
 * - Authentication service
 * - Session storage
 * - Logging functionality
 *
 * It also includes platform-specific dependencies from [platformSpecificModule].
 */
val coreDataModule = module {
    includes(platformSpecificModule)
    single<CustomLogger> { AppLogger }
    single {
        HttpClientFactory(get()).create(get())
    }

    /*
    single<IAuthService> {
        KtorAuthService(get())
    }*/

    singleOf(::KtorAuthService) bind IAuthService::class
    singleOf(::DataStoreSessionStorage) bind ISessionDataStorage::class

}