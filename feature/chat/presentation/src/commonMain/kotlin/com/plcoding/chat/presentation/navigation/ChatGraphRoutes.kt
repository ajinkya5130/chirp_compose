package com.plcoding.chat.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Sealed interface representing chat-related navigation destinations.
 *
 * All routes are serializable to support type-safe navigation with Kotlin Serialization.
 */
sealed interface ChatGraphRoutes {
    /**
     * The root navigation graph for all chat-related screens.
     */
    @Serializable
    data object Graph : ChatGraphRoutes

    /**
     * Navigation route for the chat list details screen with adaptive layout.
     *
     * This screen displays a list of chats and their details in an adaptive layout
     * that responds to different screen sizes and device configurations.
     */
    @Serializable
    data object ChatListDetailsRoute : ChatGraphRoutes
}