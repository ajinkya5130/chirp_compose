package com.plcoding.chat.domain

import com.plcoding.chat.models.ChatParticipant
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.Result

interface ChatParticipantService {
    suspend fun searchParticipant(
        query: String,
    ): Result<ChatParticipant, DataError.Remote>
}