package com.plcoding.chat.presentation.di

import com.plcoding.chat.presentation.chat_screen.ChatListViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val chatModule = module {
    viewModelOf(::ChatListViewModel)
}