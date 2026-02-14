package com.plcoding.chat.presentation.di

import com.plcoding.chat.presentation.chat_details_screen.ChatListDetailsViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

/**
 * Koin dependency injection module for chat presentation layer.
 *
 * This module provides:
 * - [ChatListDetailsViewModel] for managing chat list and details screen state
 */
val chatModule = module {
    viewModelOf(::ChatListDetailsViewModel)
}