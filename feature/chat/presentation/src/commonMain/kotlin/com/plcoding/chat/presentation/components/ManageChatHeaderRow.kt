package com.plcoding.chat.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.cancel
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.extended
import org.jetbrains.compose.resources.stringResource

/**
 * Composable that displays a header row for chat management screens.
 *
 * This header shows a title on the left and a close button on the right,
 * typically used in dialogs or sheets for creating or editing chats.
 *
 * @param modifier Modifier to be applied to the root container.
 * @param title The title text to display in the header.
 * @param onCloseClick Callback invoked when the close button is clicked.
 */
@Composable
fun ManageChatHeaderRow(
    modifier: Modifier = Modifier,
    title: String,
    onCloseClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(
                horizontal = LocalDim.current.dimen20dp,
                vertical = LocalDim.current.dimen16dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.extended.textPrimary,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onCloseClick) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(Res.string.cancel),
                tint = MaterialTheme.colorScheme.extended.textSecondary
            )
        }
    }

}