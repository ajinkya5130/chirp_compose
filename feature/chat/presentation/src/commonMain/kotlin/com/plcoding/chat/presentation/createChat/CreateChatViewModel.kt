/**
Created by ajinkyak on 14/02/26
 */
package com.plcoding.chat.presentation.createChat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

/**
 * ViewModel for the Create Chat screen.
 *
 * This ViewModel manages the state and business logic for creating a new chat,
 * including participant search, selection, and chat creation operations.
 * It exposes a state flow that the UI observes for rendering and handles
 * user actions through the [onAction] method.
 */
class CreateChatViewModel : ViewModel() {

    /**
     * Flag to track whether initial data has been loaded to prevent redundant loading.
     */
    private var hasLoadedInitialData = false

    /**
     * Internal mutable state flow for managing the create chat screen state.
     */
    private val _state = MutableStateFlow(CreateChatScreenState())

    /**
     * Public state flow that emits the current create chat screen state.
     * Automatically loads initial data on first subscription.
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
            initialValue = CreateChatScreenState()
        )

    /**
     * Handles user actions from the Create Chat screen.
     *
     * This method processes all user interactions and updates the state accordingly.
     *
     * @param action The action to be processed, defined by [CreateChatScreenAction].
     */
    fun onAction(action: CreateChatScreenAction) {
        when (action) {
            CreateChatScreenAction.OnCreateChatClick -> {
                // TODO: Implement chat creation logic
            }

            CreateChatScreenAction.onAddClick -> {
                // TODO: Implement participant addition logic
            }

            CreateChatScreenAction.onDismissDialog -> {
                // TODO: Implement dialog dismissal logic
            }
        }
    }

}