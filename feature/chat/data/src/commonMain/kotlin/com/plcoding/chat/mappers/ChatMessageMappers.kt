package com.plcoding.chat.mappers

import com.plcoding.chat.dto.ChatMessageDto
import com.plcoding.chat.models.ChatMessage
import kotlin.time.Instant

/**
 * Converts a [ChatMessageDto] from the data layer to a [ChatMessage] domain model.
 *
 * This extension function maps the DTO representation received from the API
 * to the domain model used throughout the application. It parses the ISO 8601
 * timestamp string to an [Instant].
 *
 * @return The domain model representation of the chat message.
 */
fun ChatMessageDto.toDomain(): ChatMessage {
    return ChatMessage(
        id = id,
        chatId = chatId,
        content = content,
        createdAt = Instant.parse(createdAt),
        senderId = senderId,
    )
}