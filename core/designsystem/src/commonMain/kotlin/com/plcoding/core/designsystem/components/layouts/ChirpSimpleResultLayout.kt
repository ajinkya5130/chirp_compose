package com.plcoding.core.designsystem.components.layouts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.plcoding.core.designsystem.components.branding.ChirpSuccessLogo
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.buttons.ChirpButtonStyle
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ChirpSimpleResultLayout(
    title: String,
    description: String,
    icon: @Composable ColumnScope.() -> Unit,
    primaryButtonText: @Composable () -> Unit,
    secondaryButton: @Composable (() -> Unit)? = null,
    secondaryErrorText: String? = null,
    modifier: Modifier = Modifier,
) {
    val dimen = LocalDim.current
    Column(
        modifier = modifier
            .padding(
                horizontal = dimen.dimen16dp,
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()
        Column(
            modifier = Modifier.fillMaxWidth().offset(y = dimen.dimenMinus25dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                title,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.extended.textPrimary,
                textAlign = TextAlign.Center
            )
            ChirpSpacerHeight(dimen.dimen8dp)
            Text(
                description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.extended.textSecondary,
                textAlign = TextAlign.Center
            )
            ChirpSpacerHeight(dimen.dimen24dp)
            primaryButtonText()
            if (secondaryButton != null) {
                ChirpSpacerHeight(dimen.dimen8dp)
                secondaryButton.invoke()
            }
            ChirpSpacerHeight(dimen.dimen8dp)
            if (secondaryErrorText != null) {
                Text(
                    secondaryErrorText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

}

@Composable
@Preview(showBackground = true)
fun ChirpSimpleSuccessLayoutPreview() {
    ChirpTheme {
        ChirpSimpleResultLayout(
            "Chirp Success",
            "Registered !",
            icon = {
                ChirpSuccessLogo()
            },
            primaryButtonText = {
                ChirpButton(
                    text = "Login", onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = "Resend", onClick = {},
                    style = ChirpButtonStyle.SECONDARY,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}

@Composable
@Preview
fun ChirpSimpleSuccessLayoutDarkPreview() {
    ChirpTheme(darkTheme = true) {
        ChirpSimpleResultLayout(
            "Chirp Success",
            "Registered !",
            icon = {
                ChirpSuccessLogo()
            },
            primaryButtonText = {
                ChirpButton(
                    text = "Login", onClick = {},
                    modifier = Modifier.fillMaxWidth()
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = "Resend", onClick = {},
                    style = ChirpButtonStyle.SECONDARY,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        )
    }
}