package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.plcoding.auth.presentation.navigation.AuthGraphRoutes
import com.plcoding.auth.presentation.navigation.authGraph
import com.plcoding.chat.presentation.navigation.ChatGraphRoutes
import com.plcoding.chat.presentation.navigation.chatGraph

/**
 * Root navigation composable for the application.
 *
 * Sets up the navigation graph with authentication and chat screens,
 * handling navigation between different parts of the app.
 *
 * @param navController The navigation controller for managing navigation
 * @param graphMainRoute The starting destination route (either [AuthGraphRoutes.Graph] or [ChatGraphRoutes.Graph])
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
                navController.navigate(ChatGraphRoutes.Graph) {
                    popUpTo(AuthGraphRoutes.Graph) {
                        inclusive = true
                    }
                }
            })
        chatGraph(navController)
    }
}