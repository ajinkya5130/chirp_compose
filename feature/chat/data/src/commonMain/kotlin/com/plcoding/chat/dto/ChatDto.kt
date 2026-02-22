package com.plcoding.chat.dto

import kotlinx.serialization.Serializable

/**
 * Data transfer object representing a chat from the API.
 *
 * This DTO is used for serializing and deserializing chat data when communicating
 * with the backend API. It contains the chat's basic information, participants,
 * and the most recent activity.
 *
 * @property id Unique identifier for the chat.
 * @property participants List of participants in the chat.
 * @property lastActivityAt ISO 8601 timestamp string of the last activity in the chat.
 * @property lastMessage The most recent message in the chat, or null if no messages exist.
 */
@Serializable
data class ChatDto(
    val id: String,
    val participants: List<ChatParticipantsDto>,
    val lastActivityAt: String,
    val lastMessage: ChatMessageDto?,
)
