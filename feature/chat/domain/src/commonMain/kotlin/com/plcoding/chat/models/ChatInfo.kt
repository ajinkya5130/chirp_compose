package com.plcoding.chat.models

/**
 * Domain model representing complete chat information including messages.
 *
 * This model combines chat metadata with its associated messages,
 * providing a comprehensive view of a chat conversation.
 *
 * @property chat The chat metadata and participant information.
 * @property messages List of messages in the chat with sender information.
 */
data class ChatInfo(
    val chat: ChatResponseModel,
    val messages: List<MessageWithSender>,
)