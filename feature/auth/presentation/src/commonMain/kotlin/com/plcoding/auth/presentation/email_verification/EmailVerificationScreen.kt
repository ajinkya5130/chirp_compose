/**
Created by ajinkya on 15/10/25
 */

package com.plcoding.auth.presentation.email_verification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.auth.presentation.generated.resources.Res
import chirp.feature.auth.presentation.generated.resources.close
import chirp.feature.auth.presentation.generated.resources.email_verified_failed
import chirp.feature.auth.presentation.generated.resources.email_verified_failed_desc
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully
import chirp.feature.auth.presentation.generated.resources.email_verified_successfully_desc
import chirp.feature.auth.presentation.generated.resources.login
import chirp.feature.auth.presentation.generated.resources.verifying_account
import com.plcoding.core.designsystem.components.branding.ChirpFailureLogo
import com.plcoding.core.designsystem.components.branding.ChirpSuccessLogo
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.buttons.ChirpButtonStyle
import com.plcoding.core.designsystem.components.layouts.ChirpAdaptiveResultLayout
import com.plcoding.core.designsystem.components.layouts.ChirpSimpleResultLayout
import com.plcoding.core.designsystem.components.layouts.ChirpSnackBar
import com.plcoding.core.designsystem.components.layouts.ChirpSpacerHeight
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmailVerificationScreenRoot(
    viewModel: EmailVerificationViewModel = koinViewModel(),
    onLoginClick: () -> Unit = {},
    onCloseClick: () -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    EmailVerificationScreenScreen(
        state = state,
        onAction = {
            when (it) {
                EmailVerificationScreenAction.OnLoginClick -> {
                    onLoginClick()
                }

                EmailVerificationScreenAction.OnCloseClick -> {
                    onCloseClick()
                }
            }
        }
    )
}

@Composable
fun EmailVerificationScreenScreen(
    state: EmailVerificationScreenState,
    onAction: (EmailVerificationScreenAction) -> Unit,
) {
    ChirpSnackBar {
        ChirpAdaptiveResultLayout {
            when {
                state.isVerifying -> {
                    //show verifying screen
                    VerifyingContent()

                }

                state.isVerified -> {
                    //show success screen
                    VerificationFailedContent(onAction)
                    //SuccessContent(onAction)

                }

                else -> {
                    //show verification screen
                    SuccessContent(onAction)
                }
            }

        }
    }

}

@Composable
fun VerificationFailedContent(
    onAction: (EmailVerificationScreenAction) -> Unit,
) {
    ChirpSimpleResultLayout(
        title = stringResource(Res.string.email_verified_failed),
        description = stringResource(Res.string.email_verified_failed_desc),
        icon = {
            ChirpSpacerHeight(LocalDim.current.dimen32dp)
            ChirpFailureLogo(modifier = Modifier.size(LocalDim.current.dimen60dp))
            ChirpSpacerHeight(LocalDim.current.dimen32dp)
        },
        primaryButtonText = {
            ChirpButton(
                text = stringResource(Res.string.close), onClick = {
                    onAction(EmailVerificationScreenAction.OnCloseClick)
                },
                style = ChirpButtonStyle.SECONDARY,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun SuccessContent(
    onAction: (EmailVerificationScreenAction) -> Unit,
) {
    ChirpSimpleResultLayout(
        title = stringResource(Res.string.email_verified_successfully),
        description = stringResource(Res.string.email_verified_successfully_desc),
        icon = {
            ChirpSuccessLogo()
        },
        primaryButtonText = {
            ChirpButton(
                text = stringResource(Res.string.login), onClick = {
                    onAction(EmailVerificationScreenAction.OnLoginClick)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}

@Composable
fun VerifyingContent(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.heightIn(min = LocalDim.current.dimen200dp)
            .padding(LocalDim.current.dimen16dp).fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            LocalDim.current.dimen16dp,
            Alignment.CenterVertically
        ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(LocalDim.current.dimen60dp),
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(Res.string.verifying_account),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.extended.textSecondary
        )


    }
}

@Preview
@Composable
private fun PreviewSuccessContent() {
    ChirpTheme {
        EmailVerificationScreenScreen(
            state = EmailVerificationScreenState(),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun PreviewVerifyingContent() {
    ChirpTheme {
        EmailVerificationScreenScreen(
            state = EmailVerificationScreenState(
                isVerifying = true
            ),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun PreviewVerificationFailedContent() {
    ChirpTheme {
        EmailVerificationScreenScreen(
            state = EmailVerificationScreenState(
                isVerified = true
            ),
            onAction = {}
        )
    }
}