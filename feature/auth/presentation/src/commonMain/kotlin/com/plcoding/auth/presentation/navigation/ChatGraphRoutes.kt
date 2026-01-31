package com.plcoding.auth.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Sealed interface representing chat-related navigation destinations.
 */
interface ChatGraphRoutes {
    @Serializable
    data object ChatList : ChatGraphRoutes
}