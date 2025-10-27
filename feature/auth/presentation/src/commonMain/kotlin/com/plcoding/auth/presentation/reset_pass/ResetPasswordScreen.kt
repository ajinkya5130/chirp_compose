/**
Created by ajinkya on 26/10/25
 */

package com.plcoding.auth.presentation.reset_pass

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.new_pass
import chirp.feature.auth.presentation.generated.resources.password
import chirp.feature.auth.presentation.generated.resources.password_hint
import chirp.feature.auth.presentation.generated.resources.reset_pass_successfully
import chirp.feature.auth.presentation.generated.resources.set_new_pass
import chirp.feature.auth.presentation.generated.resources.submit
import com.plcoding.core.designsystem.components.branding.ChirpBrandingLogo
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.layouts.ChirpAdaptiveFormLayout
import com.plcoding.core.designsystem.components.layouts.ChirpSpacerHeight
import com.plcoding.core.designsystem.components.textfields.ChirpPasswordField
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResetPasswordScreenRoot(
    viewModel: ResetPasswordScreenViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ResetPasswordScreenScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun ResetPasswordScreenScreen(
    state: ResetPasswordScreenState,
    onAction: (ResetPasswordScreenAction) -> Unit,
) {
    ChirpAdaptiveFormLayout(
        header = stringResource(Res.string.set_new_pass),
        errorText = state.errorText?.asString(),
        logo = {
            ChirpBrandingLogo()
        }
    ) {
        ChirpPasswordField(
            state = state.passwordField,
            modifier = Modifier.fillMaxWidth(),
            placeHolder = stringResource(Res.string.password),
            isPasswordVisible = state.passwordToggle,
            onTogglePasswordVisibility = {
                onAction(ResetPasswordScreenAction.OnPasswordToggle)
            },
            isError = state.errorText != null,
            title = stringResource(Res.string.new_pass),
            supportingText = stringResource(Res.string.password_hint)
        )
        ChirpSpacerHeight(LocalDim.current.dimen16dp)
        ChirpButton(
            text = stringResource(Res.string.submit),
            onClick = {
                onAction(ResetPasswordScreenAction.OnSubmitClick)
            },
            isLoading = state.isLoading,
            enabled = state.canSubmit && state.isLoading.not(),
            modifier = Modifier.fillMaxWidth(),
        )

        if (state.isPasswordResetSuccessfully) {
            Text(
                text = stringResource(Res.string.reset_pass_successfully),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.extended.success,
                style = MaterialTheme.typography.bodyMedium
            )
        }

    }

}

@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        ResetPasswordScreenScreen(
            state = ResetPasswordScreenState(),
            onAction = {}
        )
    }
}