package com.plcoding.chat.presentation.chat_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.plcoding.core.designsystem.theme.extended
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ChatListScreen(
    viewModel: ChatListViewModel = koinViewModel(),
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Chat List Screen",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.extended.textPrimary,
        )
    }
}