/**
Created by ajinkya on 11/10/25
 */

package com.plcoding.auth.presentation.register.register_success

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.chirp_successfully_created
import chirp.feature.auth.presentation.generated.resources.email_authentication_email_sent
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.resend_verification_email
import com.plcoding.core.designsystem.components.branding.ChirpSuccessLogo
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.buttons.ChirpButtonStyle
import com.plcoding.core.designsystem.components.layouts.ChirpAdaptiveResultLayout
import com.plcoding.core.designsystem.components.layouts.ChirpSimpleSuccessLayout
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RegisterSuccessRoot(
    viewModel: RegisterSuccessViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    RegisterSuccessScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
internal fun RegisterSuccessScreen(
    state: RegisterSuccessState,
    onAction: (RegisterSuccessAction) -> Unit,
) {
    ChirpAdaptiveResultLayout {
        ChirpSimpleSuccessLayout(
            title = stringResource(Res.string.chirp_successfully_created),
            description = stringResource(Res.string.email_authentication_email_sent, state.email),
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
            }
        )

    }
}

@Preview
@Composable
private fun PreviewRegisterSuccessScreen() {
    ChirpTheme {
        RegisterSuccessScreen(
            state = RegisterSuccessState(),
            onAction = {}
        )
    }
}