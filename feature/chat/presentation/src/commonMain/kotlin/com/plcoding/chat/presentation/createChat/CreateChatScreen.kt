/**
Created by ajinkyak on 14/02/26
 */

package com.plcoding.chat.presentation.createChat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.cancel
import chirp.feature.chat.presentation.generated.resources.create_chat
import com.plcoding.chat.models.ChatResponseModel
import com.plcoding.chat.presentation.components.ChatMemberSearchSection
import com.plcoding.chat.presentation.components.ChatParticipantSelectionSection
import com.plcoding.chat.presentation.components.ManageChatButtonSection
import com.plcoding.chat.presentation.components.ManageChatHeaderRow
import com.plcoding.core.designsystem.components.avatar.ChatParticipantUi
import com.plcoding.core.designsystem.components.buttons.ChirpButton
import com.plcoding.core.designsystem.components.buttons.ChirpButtonStyle
import com.plcoding.core.designsystem.components.dialogs.ChirpAdaptiveDialogSheetLayout
import com.plcoding.core.designsystem.components.layouts.ChirpHorizontalDivider
import com.plcoding.core.designsystem.dimesions.LocalDim
import com.plcoding.core.designsystem.theme.ChirpTheme
import com.plcoding.core.presentation.utils.DeviceConfiguration
import com.plcoding.core.presentation.utils.ObserveAsEvent
import com.plcoding.core.presentation.utils.clearFocusOnTap
import com.plcoding.core.presentation.utils.rememberDeviceConfiguration
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * Root composable for the Create Chat screen.
 *
 * This is the entry point for the Create Chat feature. It sets up the ViewModel,
 * collects the state, and wraps the screen in an adaptive dialog/sheet layout
 * that adjusts based on the device configuration.
 *
 * @param viewModel The ViewModel that manages the create chat state and business logic.
 *                  Defaults to a Koin-injected instance.
 */
@Composable
fun CreateChatScreenRoot(
    viewModel: CreateChatViewModel = koinViewModel(),
    onDismiss: () -> Unit = {},
    onChatCreated: (chat: ChatResponseModel) -> Unit = {},
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(
        flow = viewModel.events,
        onEvent = { event ->
            when (event) {
                is CreateChatEvent.OnCreateChatClick -> {
                    onChatCreated(event.chat)
                }
            }
        }
    )

    ChirpAdaptiveDialogSheetLayout(
        onDismiss = {
            onDismiss
        }
    ) {
        CreateChatScreen(
            state = state,
            onAction = {
                when (it) {
                    CreateChatScreenAction.onDismissDialog -> onDismiss()
                    else -> viewModel.onAction(it)
                }
            }
        )
    }
}

/**
 * Internal composable that renders the Create Chat screen UI.
 *
 * This composable displays the chat creation interface with adaptive behavior based on
 * device configuration and keyboard visibility. It includes a search section for finding
 * participants, a selection section showing selected participants, and action buttons.
 *
 * The header row is conditionally hidden in landscape mode on mobile devices or when
 * the keyboard is visible to maximize screen space.
 *
 * @param state The current state of the create chat screen.
 * @param onAction Callback to handle user actions.
 */
@Composable
private fun CreateChatScreen(
    state: CreateChatScreenState,
    onAction: (CreateChatScreenAction) -> Unit,
) {
    var isTextFieldFocused by rememberSaveable { mutableStateOf(false) }
    val imeHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val isKeyboardVisible = imeHeight > 0
    val currentDeviceConfig = rememberDeviceConfiguration()

    val shouldHideHeaderRow = currentDeviceConfig == DeviceConfiguration.MOBILE_LANDSCAPE
            || (isKeyboardVisible && currentDeviceConfig != DeviceConfiguration.DESKTOP) || isTextFieldFocused

    Column(
        modifier = Modifier
            .clearFocusOnTap()
            .fillMaxWidth()
            .wrapContentHeight()
            .imePadding()
            .background(MaterialTheme.colorScheme.surface)
            .navigationBarsPadding()
    ) {
        AnimatedVisibility(!shouldHideHeaderRow) {
            Column {
                ManageChatHeaderRow(
                    title = stringResource(Res.string.create_chat),
                    onCloseClick = { onAction(CreateChatScreenAction.onDismissDialog) },
                    modifier = Modifier.fillMaxWidth()
                )
                ChirpHorizontalDivider()
            }
        }
        ChatMemberSearchSection(
            modifier = Modifier.fillMaxWidth(),
            textFieldState = state.queryTexState,
            onAddClick = {
                onAction(CreateChatScreenAction.onAddClick)
            },
            isSearchEnabled = state.canAddParticipant,
            isLoading = state.isSearchingParticipants,
            errorText = state.searchError,
            onFocusChanged = {
                isTextFieldFocused = it
            }
        )
        ChatParticipantSelectionSection(
            modifier = Modifier.fillMaxWidth(),
            selectedParticipants = state.selectedParticipants,
            searchResult = state.currentSearchResult,
        )
        ChirpHorizontalDivider()
        ManageChatButtonSection(
            modifier = Modifier.fillMaxWidth().padding(
                LocalDim.current.dimen12dp
            ),
            primaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.create_chat),
                    onClick = { onAction(CreateChatScreenAction.OnCreateChatClick) },
                    enabled = state.selectedParticipants.isNotEmpty(),
                    isLoading = state.isCreatingChat
                )
            },
            secondaryButton = {
                ChirpButton(
                    text = stringResource(Res.string.cancel),
                    onClick = { onAction(CreateChatScreenAction.onDismissDialog) },
                    style = ChirpButtonStyle.SECONDARY
                )
            },
            error = state.createChatError?.asString()
        )
    }

}

/**
 * Preview composable for the Create Chat screen.
 *
 * Displays a preview of the create chat screen with sample data including
 * multiple selected participants and a search result.
 */
@Preview
@Composable
private fun Preview() {
    ChirpTheme {
        CreateChatScreen(
            state = CreateChatScreenState(
                selectedParticipants = listOf(
                    ChatParticipantUi(
                        id = "1",
                        username = "John",
                        initials = "JO",
                        imageUrl = null
                    ),
                    ChatParticipantUi(
                        id = "2",
                        username = "Jane",
                        initials = "JA",
                        imageUrl = null
                    ),
                    ChatParticipantUi(
                        id = "3",
                        username = "Doe",
                        initials = "DO",
                        imageUrl = null
                    )
                ),
                currentSearchResult = ChatParticipantUi(
                    id = "4",
                    username = "John",
                    imageUrl = null,
                    initials = "JO",
                )
            ),
            onAction = {}
        )
    }
}