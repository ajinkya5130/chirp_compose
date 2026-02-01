package com.plcoding.chat.models

data class ChatInfo(
    val chat: ChatUi,
    val messages: List<MessageWithSender>,
)