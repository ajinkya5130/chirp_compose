/**
Created by ajinkya on 18/10/25
 */

package com.plcoding.auth.presentation.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.create_account
import chirp.feature.auth.presentation.generated.resources.dismiss
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.email_not_verified_message
import chirp.feature.auth.presentation.generated.resources.email_not_verified_title
import chirp.feature.auth.presentation.generated.resources.email_placeholder
import chirp.feature.auth.presentation.generated.resources.forgot_password
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import chirp.feature.auth.presentation.generated.resources.welcome_back
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
fun LoginScreenRoot(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: () -> Unit = {},
    onForgotPassword: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onEmailResentSuccess: (email: String) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    var showEmailNotVerifiedDialog by remember { mutableStateOf(false) }

    ObserveAsEvent(viewModel.events) { event ->
        when (event) {
            is LoginEvent.OnLoginSuccess -> {
                onLoginSuccess()
            }

            LoginEvent.OnEmailNotVerified -> {
                showEmailNotVerifiedDialog = true
            }

            is LoginEvent.OnEmailResentSuccess -> {
                showEmailNotVerifiedDialog = false
                onEmailResentSuccess(event.email)
            }
        }
    }

    if (showEmailNotVerifiedDialog) {
        EmailNotVerifiedDialog(
            onDismiss = { showEmailNotVerifiedDialog = false },
            resendVerificationEmail = {
                showEmailNotVerifiedDialog = false
                viewModel.resendVerificationEmail()
            }
        )
    }

    LoginScreen(
        state = state,
        onAction = {
            when (it) {
                LoginScreenAction.OnForgotPasswordClick -> onForgotPassword()
                LoginScreenAction.OnRegisterClick -> onRegisterClick()
                else -> Unit
            }
            viewModel.onAction(it)
        }
    )
}

@Composable
fun LoginScreen(
    state: LoginScreenState,
    onAction: (LoginScreenAction) -> Unit,
) {
    ChirpSnackBar {
        ChirpAdaptiveFormLayout(
            header = stringResource(Res.string.welcome_back),
            errorText = state.loginError?.asString(),
            logo = {
                ChirpBrandingLogo()
            }
        ) {
            //form content
            //email text field
            ChirpTextField(
                state = state.emailTextFieldState,
                placeHolder = stringResource(Res.string.email_placeholder),
                title = stringResource(Res.string.email),
                keyboardType = KeyboardType.Email,
                singleLine = true
            )
            ChirpSpacerHeight(LocalDim.current.dimen16dp)
            //password text field
            ChirpPasswordField(
                state = state.passwordTextFieldState,
                placeHolder = stringResource(Res.string.password),
                title = stringResource(Res.string.password),
                isPasswordVisible = state.isPasswordVisible,
                onTogglePasswordVisibility = {
                    onAction(LoginScreenAction.OnTogglePasswordVisibilityClick)
                }
            )
            ChirpSpacerHeight(LocalDim.current.dimen16dp)
            //forgot password
            Text(
                text = stringResource(Res.string.forgot_password),
                modifier = Modifier.align(Alignment.End).clickable {
                    onAction(LoginScreenAction.OnForgotPasswordClick)
                },
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.titleSmall
            )
            //login button
            ChirpSpacerHeight(LocalDim.current.dimen24dp)
            ChirpButton(
                text = stringResource(Res.string.login),
                onClick = {
                    onAction(LoginScreenAction.OnLoginClick)
                },
                enabled = state.canLogin,
                isLoading = state.isLoggingIn,
                modifier = Modifier.fillMaxWidth()
            )
            //register button
            ChirpSpacerHeight(LocalDim.current.dimen16dp)
            ChirpButton(
                text = stringResource(Res.string.create_account),
                onClick = {
                    onAction(LoginScreenAction.OnRegisterClick)
                },
                style = ChirpButtonStyle.SECONDARY,
                modifier = Modifier.fillMaxWidth()
            )

        }

    }
}

@Composable
fun EmailNotVerifiedDialog(
    onDismiss: () -> Unit,
    resendVerificationEmail: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = stringResource(Res.string.email_not_verified_title),
                style = MaterialTheme.typography.titleLarge
            )
        },
        text = {
            Text(
                text = stringResource(Res.string.email_not_verified_message),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        confirmButton = {
            ChirpButton(
                text = stringResource(Res.string.resend_verification_email),
                onClick = resendVerificationEmail,
                style = ChirpButtonStyle.PRIMARY
            )
        },
        dismissButton = {
            ChirpButton(
                text = stringResource(Res.string.dismiss),
                onClick = onDismiss,
                style = ChirpButtonStyle.DESTRUCTIVE_SECONDARY
            )
        }
    )
}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        LoginScreen(
            state = LoginScreenState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun PreviewLoginDark() {
    ChirpTheme(darkTheme = true) {
        LoginScreen(
            state = LoginScreenState(),
            onAction = {}
        )
    }
}