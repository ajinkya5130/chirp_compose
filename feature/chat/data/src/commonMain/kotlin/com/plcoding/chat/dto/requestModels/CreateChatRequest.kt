package com.plcoding.chat.dto.requestModels

import kotlinx.serialization.Serializable

/**
 * Request model for creating a new chat.
 *
 * This data class represents the payload sent to the API when creating a new chat.
 * It contains the list of user IDs who should be added as participants.
 *
 * @property otherUserIds List of user IDs to include as participants in the new chat.
 */
@Serializable
data class CreateChatRequest(
    val otherUserIds: List<String>,
)
