package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.plcoding.auth.presentation.navigation.AuthGraphRoutes
import com.plcoding.auth.presentation.navigation.ChatGraphRoutes
import com.plcoding.auth.presentation.navigation.authGraph
import com.plcoding.chat.presentation.chat_screen.ChatListScreen

/**
 * Root navigation composable for the application.
 *
 * Sets up the navigation graph with authentication and chat screens,
 * handling navigation between different parts of the app.
 *
 * @param navController The navigation controller for managing navigation
 */
@Composable
fun NavigationRoot(
    navController: NavHostController,
    graphMainRoute: Any,
) {
    NavHost(
        navController = navController,
        startDestination = graphMainRoute
    ) {
        authGraph(
            navController,
            onLoginSuccess = {
                navController.navigate(ChatGraphRoutes.ChatList) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = true
                    }
                }
            })
        composable<ChatGraphRoutes.ChatList> {
            ChatListScreen()
        }
    }
}