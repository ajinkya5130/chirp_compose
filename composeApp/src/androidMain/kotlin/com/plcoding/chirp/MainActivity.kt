package com.plcoding.chirp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    private var keepSplashScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        // Keep the splash screen visible for a specified duration
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        setContent {
            App(
                onAuthenticationChecked = {
                    keepSplashScreen = false
                }
            )
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}