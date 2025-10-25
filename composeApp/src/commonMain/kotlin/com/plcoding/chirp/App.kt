package com.plcoding.chirp

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.plcoding.auth.presentation.navigation.AuthGraphRoutes
import com.plcoding.auth.presentation.navigation.ChatGraphRoutes
import com.plcoding.chirp.navigation.DeepLinkListener
import com.plcoding.chirp.navigation.NavigationRoot
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App(
    viewModel: MainViewModel = koinViewModel(),
    onAuthenticationChecked: () -> Unit = {},
) {
    val navController = rememberNavController()
    DeepLinkListener(navController)
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(state.isCheckingAuth) {
        if (state.isCheckingAuth.not()) {
            onAuthenticationChecked.invoke()
        }
    }
    ChirpTheme {
        if (state.isCheckingAuth.not()) {
            NavigationRoot(
                navController,
                if (state.isLoggedIn) {
                    ChatGraphRoutes.ChatList
                } else {
                    AuthGraphRoutes.Graph
                }
            )
        }
        /*
        var showContent by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Button(onClick = { showContent = !showContent }) {
                Text("Click me!")
            }
            AnimatedVisibility(showContent) {
                val greeting = remember { Greeting().greet() }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(painterResource(Res.drawable.compose_multiplatform), null)
                    Text("Compose: $greeting")
                }
            }
        }*/
    }
}