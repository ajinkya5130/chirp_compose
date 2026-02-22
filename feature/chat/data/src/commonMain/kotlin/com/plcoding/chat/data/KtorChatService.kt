package com.plcoding.chat.data

import com.plcoding.chat.domain.ChatService
import com.plcoding.chat.dto.ChatDto
import com.plcoding.chat.dto.requestModels.CreateChatRequest
import com.plcoding.chat.mappers.toDomain
import com.plcoding.chat.models.ChatResponseModel
import com.plcoding.core.data.networking.post
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.Result
import com.plcoding.core.domain.utils.map
import io.ktor.client.HttpClient

/**
 * Ktor-based implementation of [ChatService] for managing chat operations.
 *
 * This service handles HTTP communication with the chat API using Ktor client,
 * performing operations such as creating new chats and managing chat data.
 *
 * @property httpClient The Ktor HTTP client used for making network requests.
 */
class KtorChatService(
    private val httpClient: HttpClient,
) : ChatService {
    /**
     * Creates a new chat with the specified participants.
     *
     * Sends a POST request to the `/chats` endpoint with the list of participant IDs
     * and returns the created chat information.
     *
     * @param participantIds List of user IDs to include as participants in the chat.
     * @return [Result] containing either the created [ChatResponseModel] on success,
     *         or a [DataError.Remote] on failure.
     */
    override suspend fun createChat(participantIds: List<String>): Result<ChatResponseModel, DataError.Remote> {
        return httpClient.post<CreateChatRequest, ChatDto>(
            route = "/chats",
            body = CreateChatRequest(participantIds)
        ).map {
            it.toDomain()
        }
    }

}