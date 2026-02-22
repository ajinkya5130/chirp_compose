package com.plcoding.chat.di

import com.plcoding.chat.data.KtorChatParticipantService
import com.plcoding.chat.data.KtorChatService
import com.plcoding.chat.domain.ChatParticipantService
import com.plcoding.chat.domain.ChatService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * Koin dependency injection module for the chat data layer.
 *
 * This module provides singleton instances of chat-related services,
 * binding their implementations to their respective interfaces.
 *
 * Provided dependencies:
 * - [ChatParticipantService] - Implemented by [KtorChatParticipantService]
 * - [ChatService] - Implemented by [KtorChatService]
 */
val chatDataModule = module {
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
    singleOf(::KtorChatService) bind ChatService::class
}