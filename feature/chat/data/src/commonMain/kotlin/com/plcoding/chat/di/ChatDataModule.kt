package com.plcoding.chat.di

import com.plcoding.chat.data.KtorChatParticipantService
import com.plcoding.chat.domain.ChatParticipantService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val chatDataModule = module {
    singleOf(::KtorChatParticipantService) bind ChatParticipantService::class
}