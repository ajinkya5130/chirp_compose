/**
Created by ajinkyak on 13/02/26
 */

package com.plcoding.chat.presentation.chat_details_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.create_chat
import com.plcoding.chat.presentation.createChat.CreateChatScreenRoot
import com.plcoding.core.designsystem.components.buttons.ChirpFloatingActionButton
import com.plcoding.core.designsystem.theme.extended
import com.plcoding.core.presentation.utils.DialogSheetScopedViewModel
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * Root composable for the chat list details adaptive layout.
 *
 * This composable serves as the entry point for the adaptive layout screen,
 * managing the view model and state collection.
 *
 * @param viewModel The view model managing the chat list details state and actions
 */
@Composable
fun ChatListDetailsAdaptiveLayoutRoot(
    viewModel: ChatListDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ChatListDetailsAdaptiveLayoutScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

/**
 * Adaptive layout screen displaying a list-detail pattern for chats.
 *
 * This composable implements a responsive list-detail layout that adapts to different
 * screen sizes and configurations. On smaller screens, it shows either the list or detail pane.
 * On larger screens, both panes are displayed side by side.
 *
 * Features:
 * - Adaptive layout based on screen size and device configuration
 * - No horizontal spacing between panes
 * - Back navigation support for detail pane
 * - Smooth animations between panes
 *
 * @param state The current state of the chat list details screen
 * @param onAction Callback for handling user actions
 */
@OptIn(ExperimentalMaterial3AdaptiveApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ChatListDetailsAdaptiveLayoutScreen(
    state: ChatListDetailsState,
    onAction: (ChatListDetailsAction) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaffoldDirective = createNoSpacingPaneScaffoldDirective()
    val scaffoldNavigator =
        rememberListDetailPaneScaffoldNavigator(scaffoldDirective = scaffoldDirective)
    BackHandler(enabled = scaffoldNavigator.canNavigateBack()) {
        scope.launch {
            scaffoldNavigator.navigateBack()
        }
    }
    ListDetailPaneScaffold(
        directive = scaffoldDirective,
        value = scaffoldNavigator.scaffoldValue,
        modifier = Modifier.background(MaterialTheme.colorScheme.extended.surfaceLower),
        listPane = {
            AnimatedPane {
                Scaffold(
                    modifier = Modifier.fillMaxWidth(),
                    floatingActionButton = {
                        ChirpFloatingActionButton(
                            onClick = {
                                onAction.invoke(ChatListDetailsAction.OnCreateChatClick)
                            },
                            content = {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = stringResource(Res.string.create_chat)
                                )
                            }
                        )
                    }
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(100) {
                            Text(
                                text = "Item $it",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.extended.textPrimary,
                                modifier = Modifier.clickable {
                                    onAction.invoke(ChatListDetailsAction.OnCreateChatClick)
                                    onAction.invoke(ChatListDetailsAction.OnChatClick(it.toString()))
                                    scope.launch {
                                        scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail)
                                    }
                                }.padding(16.dp)
                            )
                        }
                    }
                }
            }

        },
        detailPane = {
            AnimatedPane {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    state.selectedChatId?.let {
                        Text(
                            text = "Detail Pane item selected: ${state.selectedChatId}",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.extended.textPrimary,
                        )
                    }
                }
            }

        }

    )

    DialogSheetScopedViewModel(
        visible = state.dialogState is DialogState.CreateChat
    ) {
        CreateChatScreenRoot(
            onDismiss = {
                onAction.invoke(ChatListDetailsAction.OnDismissDialog)
            },
            onChatCreated = { chat ->
                onAction.invoke(ChatListDetailsAction.OnDismissDialog)
                scope.launch {
                    scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.List)
                }
                onAction.invoke(ChatListDetailsAction.OnChatClick(chat.id))
            }
        )
    }
}

