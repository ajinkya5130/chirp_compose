package com.plcoding.chat.domain

import com.plcoding.chat.models.ChatResponseModel
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.Result

/**
 * Service interface for managing chat operations.
 *
 * This interface defines the contract for chat-related business logic,
 * including creating new chats and managing chat data.
 */
interface ChatService {
    /**
     * Creates a new chat with the specified participants.
     *
     * @param participantIds List of user IDs to include as participants in the chat.
     * @return [Result] containing either the created [ChatResponseModel] on success,
     *         or a [DataError.Remote] on failure.
     */
    suspend fun createChat(participantIds: List<String>): Result<ChatResponseModel, DataError.Remote>

}