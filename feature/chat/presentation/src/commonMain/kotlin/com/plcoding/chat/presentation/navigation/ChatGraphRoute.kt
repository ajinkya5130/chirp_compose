package com.plcoding.chat.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.chat.presentation.chat_details_screen.ChatListDetailsAdaptiveLayoutRoot

/**
 * Extension function to add the chat navigation graph to the NavGraphBuilder.
 *
 * This function sets up the chat-related navigation destinations, including:
 * - Chat list details screen with adaptive layout
 *
 * @param navController The navigation controller for handling navigation actions
 */
fun NavGraphBuilder.chatGraph(
    navController: NavController,
) {
    navigation<ChatGraphRoutes.Graph>(
        startDestination = ChatGraphRoutes.ChatListDetailsRoute
    ) {
        composable<ChatGraphRoutes.ChatListDetailsRoute> {
            ChatListDetailsAdaptiveLayoutRoot()
        }
    }
}