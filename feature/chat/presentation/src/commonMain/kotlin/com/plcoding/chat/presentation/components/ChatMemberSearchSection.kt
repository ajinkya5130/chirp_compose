package com.plcoding.chat.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.add_chat
import chirp.feature.chat.presentation.generated.resources.email_or_username
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.buttons.ChirpButtonStyle
import com.plcoding.core.designsystem.components.textfields.ChirpTextField
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.presentation.utils.UiText
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * Composable that displays the chat member search section.
 *
 * This section includes a text field for searching participants by email or username,
 * and an "Add" button to add the found participant to the chat. The search field
 * supports error states and focus tracking.
 *
 * @param modifier Modifier to be applied to the root container.
 * @param textFieldState The state of the search text field.
 * @param isSearchEnabled Whether the search and add functionality is enabled.
 * @param isLoading Whether a search operation is currently in progress.
 * @param errorText Optional error message to display below the text field.
 * @param onFocusChanged Callback invoked when the text field focus state changes.
 * @param onAddClick Callback invoked when the "Add" button is clicked.
 */
@Composable
fun ChatMemberSearchSection(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = TextFieldState("sample"),
    isSearchEnabled: Boolean = true,
    isLoading: Boolean = false,
    errorText: UiText? = null,
    onFocusChanged: (Boolean) -> Unit = {},
    onAddClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = LocalDim.current.dimen16dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(LocalDim.current.dimen16dp)
    ) {
        ChirpTextField(
            modifier = Modifier.weight(1f),
            supportingText = errorText?.asString(),
            onFocusChanged = onFocusChanged,
            state = textFieldState,
            placeHolder = stringResource(Res.string.email_or_username),
            isError = errorText != null,
            singleLine = true,
            keyboardType = KeyboardType.Email,


            )

        ChirpButton(
            stringResource(Res.string.add_chat),
            style = ChirpButtonStyle.SECONDARY,
            isLoading = isLoading,
            onClick = onAddClick,
            enabled = isSearchEnabled,

            )

    }
}

/**
 * Preview composable for the Chat Member Search section.
 *
 * Displays a preview of the search section with default parameters.
 */
@Composable
@Preview()
fun ChatMemberPreview() {
    ChirpTheme {
        ChatMemberSearchSection(

        )
    }
}