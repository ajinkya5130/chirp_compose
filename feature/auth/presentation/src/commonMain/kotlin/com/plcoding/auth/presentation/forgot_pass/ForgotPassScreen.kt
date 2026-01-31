/**
Created by ajinkya on 25/10/25
 */

package com.plcoding.auth.presentation.forgot_pass

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.email
import chirp.feature.auth.presentation.generated.resources.email_placeholder
import chirp.feature.auth.presentation.generated.resources.forgot_pass_email_sent_successfully
import chirp.feature.auth.presentation.generated.resources.forgot_password
import chirp.feature.auth.presentation.generated.resources.submit
import com.plcoding.core.designsystem.components.branding.ChirpBrandingLogo
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.plcoding.core.designsystem.components.layouts.ChirpSnackBar
import com.plcoding.core.designsystem.components.layouts.ChirpSpacerHeight
import com.plcoding.core.designsystem.components.textfields.ChirpTextField
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ForgotPassScreenRoot(
    viewModel: ForgotPassScreenViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ForgotPassScreenScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ForgotPassScreenScreen(
    state: ForgotPassScreenState,
    onAction: (ForgotPassScreenAction) -> Unit,
) {
    ChirpSnackBar {
        ChirpAdaptiveFormLayout(
            header = stringResource(Res.string.forgot_password),
            errorText = state.errorText?.asString(),
            logo = {
                ChirpBrandingLogo()
            }) {
            ChirpTextField(
                state = state.emailTextFieldState,
                placeHolder = stringResource(Res.string.email_placeholder),
                isError = state.errorText != null,
                title = stringResource(Res.string.email),
                keyboardType = KeyboardType.Email,
                singleLine = true
            )
            ChirpSpacerHeight(LocalDim.current.dimen16dp)
            ChirpButton(
                text = stringResource(Res.string.submit),
                onClick = {
                    onAction(ForgotPassScreenAction.OnSubmitClick)
                },
                enabled = state.canSubmit && !state.isLoading,
                isLoading = state.isLoading,
                modifier = Modifier.fillMaxWidth(),
            )
            ChirpSpacerHeight(LocalDim.current.dimen8dp)
            if (state.isEmailSendSuccessfully) {
                Text(
                    text = stringResource(Res.string.forgot_pass_email_sent_successfully),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.extended.success,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        ForgotPassScreenScreen(
            state = ForgotPassScreenState(),
            onAction = {}
        )
    }
}