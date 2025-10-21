package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plcoding.auth.presentation.navigation.AuthGraphRoutes
import com.plcoding.auth.presentation.navigation.authGraph
import com.plcoding.chat.presentation.chat_screen.ChatListScreen
import kotlinx.serialization.Serializable

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

sealed interface ChatGraph {
    @Serializable
    data object ChatList : ChatGraph
}