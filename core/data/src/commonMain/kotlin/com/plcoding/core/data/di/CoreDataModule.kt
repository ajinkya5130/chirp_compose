package com.plcoding.core.data.di

import com.plcoding.core.data.auth.KtorAuthService
import com.plcoding.core.data.logging.AppLogger
import com.plcoding.core.data.networking.HttpClientFactory
import com.plcoding.core.domain.auth.IAuthService
import com.plcoding.core.domain.logging.CustomLogger
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformSpecificModule: Module

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

}