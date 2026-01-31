package com.plcoding.chirp.di

import com.plcoding.chirp.MainViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appMainModule = module {
    viewModelOf(::MainViewModel)
}