/**
Created by ajinkyak on 13/02/26
 */
package com.plcoding.chat.presentation.chat_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * ViewModel for managing the state and actions of the chat list details screen.
 *
 * This ViewModel handles:
 * - Chat selection and navigation
 * - Dialog state management (create chat, profile, manage chat)
 * - Initial data loading
 *
 * The state is exposed as a [StateFlow] that automatically loads initial data
 * on first subscription and maintains the state while there are active subscribers.
 */
class ChatListDetailsViewModel : ViewModel() {

    private var hasLoadedInitialData = false

    private val _state = MutableStateFlow(ChatListDetailsState())

    /**
     * StateFlow exposing the current state of the chat list details screen.
     *
     * The flow automatically loads initial data on first collection and maintains
     * the state for 5 seconds after the last subscriber disconnects.
     */
    val state = _state
        .onStart {
            if (!hasLoadedInitialData) {
                /** Load initial data here **/
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = ChatListDetailsState()
        )

    /**
     * Handles user actions from the chat list details screen.
     *
     * @param action The action to process
     */
    fun onAction(action: ChatListDetailsAction) {
        when (action) {
            is ChatListDetailsAction.OnChatClick -> {
                _state.update {
                    it.copy(
                        selectedChatId = action.chatId,
                    )
                }
            }

            ChatListDetailsAction.OnCreateChatClick -> {
                _state.update {
                    it.copy(
                        dialogState = DialogState.CreateChat,
                    )
                }
            }

            ChatListDetailsAction.OnDismissDialog -> {
                _state.update {
                    it.copy(
                        dialogState = DialogState.Hidden,
                    )
                }
            }

            is ChatListDetailsAction.OnManageChatClick -> {
                state.value.selectedChatId?.let { id ->
                    _state.update {
                        it.copy(
                            dialogState = DialogState.ManageChat(id),
                        )
                    }
                }

            }

            ChatListDetailsAction.OnProfileClick -> {
                _state.update {
                    it.copy(
                        dialogState = DialogState.Profile,
                    )
                }
            }
        }
    }

}