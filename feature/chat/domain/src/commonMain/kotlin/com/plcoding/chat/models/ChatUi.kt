package com.plcoding.chat.models

import kotlin.time.Instant

data class ChatUi(
    val id: String,
    val participants: List<ChatParticipant>,
    val lastActivityAt: Instant,
    val lastMessage: String?,
)