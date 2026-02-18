package com.plcoding.chat.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChatParticipantsDto(
    val userId: String,
    val username: String,
    val profilePictureUrl: String? = null,
)
