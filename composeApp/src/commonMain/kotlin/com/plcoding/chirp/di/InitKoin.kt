package com.plcoding.chirp.di

import com.plcoding.auth.presentation.di.authPresentationModule
import com.plcoding.chat.presentation.di.chatModule
import com.plcoding.core.data.di.coreDataModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

/**
 * Initializes Koin dependency injection for the application.
 *
 * This function sets up Koin with all required modules for the application,
 * including core data and authentication presentation modules. Platform-specific
 * configuration can be provided through the config parameter.
 *
 * @param config Optional platform-specific Koin configuration
 */
fun initKoin(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(
            coreDataModule,
            authPresentationModule,
            appMainModule,
            chatModule
        )
    }
}