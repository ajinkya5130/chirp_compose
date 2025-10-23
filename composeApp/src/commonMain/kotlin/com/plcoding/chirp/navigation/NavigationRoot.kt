package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plcoding.auth.presentation.navigation.AuthGraphRoutes
import com.plcoding.auth.presentation.navigation.authGraph
import com.plcoding.chat.presentation.chat_screen.ChatListScreen
import kotlinx.serialization.Serializable

/**
 * Root navigation composable for the application.
 *
 * Sets up the navigation graph with authentication and chat screens,
 * handling navigation between different parts of the app.
 *
 * @param navController The navigation controller for managing navigation
 */
@Composable
fun NavigationRoot(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AuthGraphRoutes.Graph
    ) {
        authGraph(
            navController,
            onLoginSuccess = {
                navController.navigate(ChatGraph.ChatList) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = true
                    }
                }
            })
        composable<ChatGraph.ChatList> {
            ChatListScreen()
        }
    }
}

/**
 * Sealed interface representing chat-related navigation destinations.
 */
sealed interface ChatGraph {
    /** Chat list screen destination */
    @Serializable
    data object ChatList : ChatGraph
}