package com.plcoding.chat.models

import kotlin.time.Instant

data class ChatMessage(
    val id: String,
    val chatId: String,
    val content: String,
    val createdAt: Instant,
    val senderId: String,
)