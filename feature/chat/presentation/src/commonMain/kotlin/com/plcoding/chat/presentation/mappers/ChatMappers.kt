package com.plcoding.chat.presentation.mappers

import com.plcoding.chat.models.ChatResponseModel
import com.plcoding.chat.presentation.model.ChatUi

/**
 * Converts a [ChatResponseModel] domain model to a [ChatUi] presentation model.
 *
 * This extension function maps the domain representation to the UI model,
 * separating participants into local and other participants based on the
 * provided local participant ID. It also resolves the last message sender's username.
 *
 * @param localParticipantId The ID of the current user to identify local participant.
 * @return The UI model representation of the chat.
 */
fun ChatResponseModel.toUi(localParticipantId: String): ChatUi {
    val (local, other) = participants.partition { it.userId == localParticipantId }
    return ChatUi(
        id = id,
        localParticipants = local.first().toUi(),
        otherParticipants = local.map { it.toUi() },
        lastMessage = lastMessage,
        lastMessageSenderUserName = participants.firstOrNull { it.userId == lastMessage?.senderId }?.username
    )
}