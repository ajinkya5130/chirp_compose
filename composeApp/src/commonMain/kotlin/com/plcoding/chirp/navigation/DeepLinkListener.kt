package com.plcoding.chirp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavUri
import com.plcoding.core.data.logging.AppLogger

/**
 * Composable that listens for deep link URIs and handles navigation.
 *
 * This function sets up a listener for external URIs (deep links) and navigates
 * to the appropriate destination when a deep link is received. The listener is
 * automatically cleaned up when the composable is disposed.
 *
 * @param navController The navigation controller to use for deep link navigation
 */
@Composable
fun DeepLinkListener(navController: NavHostController) {
    // The effect is produced only once, as `Unit` never changes
    DisposableEffect(Unit) {
        // Sets up the listener to call `NavController.navigate()`
        // for the composable that has a matching `navDeepLink` listed
        ExternalUriHandler.listener = { uri ->
            AppLogger.debugLog("DeepLinkListener: $uri")
            navController.navigate(NavUri(uri))
        }
        // Removes the listener when the composable is no longer active
        onDispose {
            ExternalUriHandler.listener = null
        }
    }
}