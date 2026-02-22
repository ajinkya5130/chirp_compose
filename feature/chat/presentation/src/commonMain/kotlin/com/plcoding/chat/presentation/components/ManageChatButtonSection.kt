package com.plcoding.chat.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.buttons.ChirpButtonStyle
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Composable that displays a button section for managing chat operations.
 *
 * This section arranges primary and secondary action buttons in a horizontal row,
 * aligned to the end. Typically used for chat creation or editing dialogs where
 * users need to confirm or cancel their actions.
 *
 * @param modifier Modifier to be applied to the root container.
 * @param primaryButton Composable lambda for the primary action button (e.g., "Create", "Save").
 * @param secondaryButton Composable lambda for the secondary action button (e.g., "Cancel").
 */
@Composable
fun ManageChatButtonSection(
    modifier: Modifier = Modifier,
    primaryButton: @Composable () -> Unit,
    secondaryButton: @Composable () -> Unit,
    error: String? = null,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(LocalDim.current.dimen16dp, Alignment.End),
            verticalAlignment = Alignment.CenterVertically
        ) {
            secondaryButton()
            primaryButton()
        }
        AnimatedVisibility(
            error != null
        ) {
            Spacer(modifier = Modifier.height(LocalDim.current.dimen16dp))
            error?.let {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = it,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.End,
                )
            }

        }
    }

}

/**
 * Preview composable for the Manage Chat Button section.
 *
 * Displays a preview showing primary and secondary buttons in the button section layout.
 */
@Composable
@Preview
private fun ManageChatButtonSectionPreview() {
    ChirpTheme {
        ManageChatButtonSection(
            primaryButton = {
                ChirpButton(
                    text = "Primary",
                    onClick = {}
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = "Secondary",
                    onClick = {},
                    style = ChirpButtonStyle.SECONDARY
                )
            }
        )
    }
}