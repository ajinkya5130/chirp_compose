package com.plcoding.chat.data

import com.plcoding.chat.domain.ChatParticipantService
import com.plcoding.chat.dto.ChatParticipantsDto
import com.plcoding.chat.mappers.toDomain
import com.plcoding.chat.models.ChatParticipant
import com.plcoding.core.data.networking.get
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.Result
import com.plcoding.core.domain.utils.map
import io.ktor.client.HttpClient

class KtorChatParticipantService(
    private val httpClient: HttpClient,
) : ChatParticipantService {
    override suspend fun searchParticipant(query: String): Result<ChatParticipant, DataError.Remote> {
        return httpClient.get<ChatParticipantsDto>(
            "/participants",
            mapOf(
                "query" to query
            )
        ).map {
            it.toDomain()
        }

    }
}