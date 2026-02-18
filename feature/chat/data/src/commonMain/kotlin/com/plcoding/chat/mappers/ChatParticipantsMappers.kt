package com.plcoding.chat.mappers

import com.plcoding.chat.dto.ChatParticipantsDto
import com.plcoding.chat.models.ChatParticipant

fun ChatParticipantsDto.toDomain(): ChatParticipant {
    return ChatParticipant(
        userId = userId,
        username = username,
        profilePictureUrl = profilePictureUrl,
    )
}