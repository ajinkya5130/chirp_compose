package com.plcoding.chat.dto

import kotlinx.serialization.Serializable

/**
 * Data transfer object representing a chat message from the API.
 *
 * This DTO is used for serializing and deserializing chat message data when
 * communicating with the backend API.
 *
 * @property id Unique identifier for the message.
 * @property chatId Identifier of the chat this message belongs to.
 * @property content The text content of the message.
 * @property createdAt ISO 8601 timestamp string when the message was created.
 * @property senderId Identifier of the user who sent the message.
 */
@Serializable
data class ChatMessageDto(
    val id: String,
    val chatId: String,
    val content: String,
    val createdAt: String,
    val senderId: String,
)
