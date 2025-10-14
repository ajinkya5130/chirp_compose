/**
Created by ajinkya on 11/10/25
 */

package com.plcoding.auth.presentation.register.register_success

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.chirp_successfully_created
import chirp.feature.auth.presentation.generated.resources.email_authentication_email_sent
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import chirp.feature.auth.presentation.generated.resources.verification_email_resent
import com.plcoding.core.designsystem.components.branding.ChirpSuccessLogo
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.buttons.ChirpButtonStyle
import com.plcoding.core.designsystem.components.layouts.ChirpAdaptiveResultLayout
import com.plcoding.core.designsystem.components.layouts.ChirpSimpleSuccessLayout
import com.plcoding.core.designsystem.components.layouts.ChirpSnackBar
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.presentation.utils.ObserveAsEvent
import org.jetbrains.compose.resources.getString
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RegisterSuccessRoot(
    viewModel: RegisterSuccessViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val snackBarHostState = remember {
        SnackbarHostState()
    }

    ObserveAsEvent(viewModel.events) { event ->
        when (event) {
            is RegisterSuccessEvent.ResendVerificationEmailSuccess -> {
                //show snackbar
                snackBarHostState.showSnackbar(message = getString(resource = Res.string.verification_email_resent))
            }
        }
    }

    RegisterSuccessScreen(
        state = state,
        onAction = viewModel::onAction,
        snackBarHostState
    )
}

@Composable
internal fun RegisterSuccessScreen(
    state: RegisterSuccessState,
    onAction: (RegisterSuccessAction) -> Unit,
    snackBarHostState: SnackbarHostState,
) {
    ChirpSnackBar(
        snackBarHostState = snackBarHostState,
    ) {
        ChirpAdaptiveResultLayout {
            ChirpSimpleSuccessLayout(
                title = stringResource(Res.string.chirp_successfully_created),
                description = stringResource(
                    Res.string.email_authentication_email_sent,
                    state.email
                ),
                icon = {
                    ChirpSuccessLogo()
                },
                primaryButtonText = {
                    ChirpButton(
                        text = stringResource(Res.string.login), onClick = {
                            onAction(RegisterSuccessAction.OnLoginClick)
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                secondaryButton = {
                    ChirpButton(
                        text = stringResource(Res.string.resend_verification_email), onClick = {
                            onAction(RegisterSuccessAction.OnResendClick)
                        },
                        style = ChirpButtonStyle.SECONDARY,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !state.isResendEnabled,
                        isLoading = state.isResendEnabled
                    )
                },
                secondaryErrorText = state.resnetErrorText?.asString()
            )

        }
    }
}

@Preview
@Composable
private fun PreviewRegisterSuccessScreen() {
    ChirpTheme {
        RegisterSuccessScreen(
            state = RegisterSuccessState(),
            onAction = {},
            SnackbarHostState()
        )
    }
}