package com.plcoding.chat.presentation.createChat

import com.plcoding.chat.models.ChatResponseModel

/**
 * Sealed interface representing one-time events emitted by the Create Chat screen.
 *
 * These events are used to communicate state changes that should trigger
 * navigation or other side effects in the UI layer.
 */
sealed interface CreateChatEvent {
    /**
     * Event emitted when a chat is successfully created.
     *
     * @property chat The newly created chat model.
     */
    data class OnCreateChatClick(val chat: ChatResponseModel) : CreateChatEvent
}