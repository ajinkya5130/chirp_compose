package com.plcoding.chat.models

import kotlin.time.Instant

/**
 * Domain model representing a chat response from the backend.
 *
 * This model contains the essential information about a chat, including
 * its participants, last activity timestamp, and the most recent message.
 *
 * @property id Unique identifier for the chat.
 * @property participants List of participants in the chat.
 * @property lastActivityAt Timestamp of the last activity in the chat.
 * @property lastMessage The most recent message in the chat, or null if no messages exist.
 */
data class ChatResponseModel(
    val id: String,
    val participants: List<ChatParticipant>,
    val lastActivityAt: Instant,
    val lastMessage: ChatMessage?,
)