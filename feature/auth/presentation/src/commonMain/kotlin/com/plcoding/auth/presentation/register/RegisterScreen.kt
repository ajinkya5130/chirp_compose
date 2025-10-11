package com.plcoding.auth.presentation.register

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.email_placeholder
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.password_hint
import chirp.feature.auth.presentation.generated.resources.register
import chirp.feature.auth.presentation.generated.resources.username
import chirp.feature.auth.presentation.generated.resources.username_hint
import chirp.feature.auth.presentation.generated.resources.username_placeholder
import chirp.feature.auth.presentation.generated.resources.welcome_to_chirp
import com.plcoding.core.designsystem.components.branding.ChirpBrandingLogo
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.buttons.ChirpButtonStyle
import com.plcoding.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.plcoding.core.designsystem.components.layouts.ChirpSnackBar
import com.plcoding.core.designsystem.components.layouts.ChirpSpacerHeight
import com.plcoding.core.designsystem.components.textfields.ChirpPasswordField
import com.plcoding.core.designsystem.components.textfields.ChirpTextField
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.presentation.utils.ObserveAsEvent
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterScreenRoot(
    viewModel: RegisterScreenViewModel = koinViewModel(),
    onRegisterSuccess: (email: String) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember {
        SnackbarHostState()
    }

    ObserveAsEvent(viewModel.events) { event ->
        when (event) {
            is RegisterEvent.Success -> {
                onRegisterSuccess(event.email)
            }
        }
    }

    RegisterScreen(
        state = state,
        onAction = viewModel::onAction,
        snackBarHostState
    )
}

@Composable
internal fun RegisterScreen(
    state: RegisterScreenState,
    onAction: (RegisterScreenAction) -> Unit,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
) {
    ChirpSnackBar(
        snackBarHostState = snackBarHostState,
    ) {
        ChirpAdaptiveFormLayout(
            modifier = Modifier.fillMaxWidth(),
            header = stringResource(Res.string.welcome_to_chirp),
            errorText = state.registraionError?.asString(),
            logo = {
                ChirpBrandingLogo()
            }
        ) {
            ChirpTextField(
                state = state.userNameTextState,
                placeHolder = stringResource(Res.string.username_placeholder),
                title = stringResource(Res.string.username),
                supportingText = state.userNameError?.asString()
                    ?: stringResource(Res.string.username_hint),
                isError = state.userNameError != null,
                onFocusChanged = {
                    onAction(RegisterScreenAction.OnInputTextFocusGain)
                }
            )
            ChirpSpacerHeight(LocalDim.current.dimen16dp)
            ChirpTextField(
                state = state.emailTextState,
                placeHolder = stringResource(Res.string.email_placeholder),
                title = stringResource(Res.string.email),
                supportingText = state.emailError?.asString(),
                isError = state.emailError != null,
                onFocusChanged = {
                    onAction(RegisterScreenAction.OnInputTextFocusGain)
                },
                keyboardType = KeyboardType.Email
            )
            ChirpSpacerHeight(LocalDim.current.dimen16dp)
            ChirpPasswordField(
                state = state.passwordTextState,
                placeHolder = stringResource(Res.string.password),
                title = stringResource(Res.string.password),
                supportingText = state.passwordError?.asString()
                    ?: stringResource(Res.string.password_hint),
                isError = state.passwordError != null,
                onFocusChanged = {
                    onAction(RegisterScreenAction.OnInputTextFocusGain)
                },
                onTogglePasswordVisibility = {
                    onAction(RegisterScreenAction.OnTogglePasswordVisibilityClick)
                },
                isPasswordVisible = state.isPasswordVisible
            )
            ChirpSpacerHeight(LocalDim.current.dimen16dp)
            ChirpButton(
                text = stringResource(Res.string.register),
                onClick = {
                    onAction(RegisterScreenAction.OnRegisterClick)
                },
                enabled = state.canRegister,
                isLoading = state.isRegistering,
                modifier = Modifier.fillMaxWidth()
            )
            ChirpSpacerHeight(LocalDim.current.dimen16dp)
            ChirpButton(
                text = stringResource(Res.string.login),
                onClick = {
                    onAction(RegisterScreenAction.OnLoginClick)
                },
                style = ChirpButtonStyle.SECONDARY,
                modifier = Modifier.fillMaxWidth()
            )
            ChirpSpacerHeight(LocalDim.current.dimen16dp)
        }

    }


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