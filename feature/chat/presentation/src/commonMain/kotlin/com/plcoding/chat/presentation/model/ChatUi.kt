package com.plcoding.chat.presentation.model

import com.plcoding.chat.models.ChatMessage
import com.plcoding.core.designsystem.components.avatar.ChatParticipantUi

/**
 * UI model representing a chat for presentation layer.
 *
 * This model contains chat information formatted for display in the UI,
 * with participants separated into local and other participants for easier
 * rendering of chat items.
 *
 * @property id Unique identifier for the chat.
 * @property localParticipants The current user's participant information.
 * @property otherParticipants List of other participants in the chat (excluding the current user).
 * @property lastMessage The most recent message in the chat, or null if no messages exist.
 * @property lastMessageSenderUserName Username of the sender of the last message, or null if no last message.
 */
data class ChatUi(
    val id: String,
    val localParticipants: ChatParticipantUi,
    val otherParticipants: List<ChatParticipantUi>,
    val lastMessage: ChatMessage? = null,
    val lastMessageSenderUserName: String? = null,
)
