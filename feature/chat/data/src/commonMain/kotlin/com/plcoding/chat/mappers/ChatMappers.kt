package com.plcoding.chat.mappers

import com.plcoding.chat.dto.ChatDto
import com.plcoding.chat.models.ChatResponseModel
import kotlin.time.Instant

/**
 * Converts a [ChatDto] from the data layer to a [ChatResponseModel] domain model.
 *
 * This extension function maps the DTO representation received from the API
 * to the domain model used throughout the application. It parses the ISO 8601
 * timestamp string to an [Instant] and converts nested DTOs to their domain equivalents.
 *
 * @return The domain model representation of the chat.
 */
fun ChatDto.toDomain(): ChatResponseModel {
    return ChatResponseModel(
        id = id,
        participants = participants.map { it.toDomain() },
        lastActivityAt = Instant.parse(lastActivityAt),
        lastMessage = lastMessage?.toDomain(),
    )
}