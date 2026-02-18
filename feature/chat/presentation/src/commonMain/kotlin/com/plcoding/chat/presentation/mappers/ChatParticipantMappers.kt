package com.plcoding.chat.presentation.mappers

import com.plcoding.chat.models.ChatParticipant
import com.plcoding.core.designsystem.components.avatar.ChatParticipantUi

fun ChatParticipant.toUi(): ChatParticipantUi {
    return ChatParticipantUi(
        id = userId,
        username = username,
        imageUrl = profilePictureUrl,
        initials = initials,
    )
}