package com.plcoding.chat.presentation.register

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterScreenViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun RegisterScreen(
    state: RegisterScreenState,
    onAction: (RegisterScreenAction) -> Unit,
) {

}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        RegisterScreen(
            state = RegisterScreenState(),
            onAction = {}
        )
    }
}