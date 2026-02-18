/**
Created by ajinkyak on 14/02/26
 */
package com.plcoding.chat.presentation.createChat

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.clearText
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import chirp.feature.chat.presentation.generated.resources.Res
import chirp.feature.chat.presentation.generated.resources.error_participant_not_found
import com.plcoding.chat.domain.ChatParticipantService
import com.plcoding.chat.presentation.mappers.toUi
import com.plcoding.core.domain.utils.DataError
import com.plcoding.core.domain.utils.onFailure
import com.plcoding.core.domain.utils.onSuccess
import com.plcoding.core.presentation.utils.UiText
import com.plcoding.core.presentation.utils.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

/**
 * ViewModel for the Create Chat screen.
 *
 * This ViewModel manages the state and business logic for creating a new chat,
 * including participant search, selection, and chat creation operations.
 * It exposes a state flow that the UI observes for rendering and handles
 * user actions through the [onAction] method.
 */
class CreateChatViewModel(
    val chatParticipantService: ChatParticipantService,
) : ViewModel() {

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
                searchFlow.launchIn(viewModelScope)
                hasLoadedInitialData = true
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = CreateChatScreenState()
        )

    @OptIn(FlowPreview::class)
    val searchFlow = snapshotFlow {
        _state.value.queryTexState.text.toString()
    }
        .debounce(1.seconds)
        .onEach {
            performSearch(it)
        }

    private fun performSearch(searchedString: String) {
        if (searchedString.isBlank()) {
            _state.update {
                it.copy(
                    currentSearchResult = null,
                    searchError = null,
                    canAddParticipant = false
                )
            }
            return
        }

        viewModelScope.launch {
            _state.update {
                it.copy(
                    isSearchingParticipants = true,
                    searchError = null,
                    canAddParticipant = false
                )
            }

            chatParticipantService.searchParticipant(searchedString)
                .onSuccess { participant ->
                    _state.update {
                        it.copy(
                            currentSearchResult = participant.toUi(),
                            isSearchingParticipants = false,
                            canAddParticipant = true,
                            searchError = null
                        )
                    }

                }
                .onFailure { error ->
                    val errorMessage = when (error) {
                        DataError.Remote.NOT_FOUND -> UiText.Resource(Res.string.error_participant_not_found)
                        else -> error.toUiText()
                    }
                    _state.update {
                        it.copy(
                            currentSearchResult = null,
                            isSearchingParticipants = false,
                            canAddParticipant = false,
                            searchError = errorMessage
                        )
                    }


                }
        }


    }

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

            }

            CreateChatScreenAction.onAddClick -> {
                addParticipants()
            }

            CreateChatScreenAction.onDismissDialog -> Unit
        }
    }

    private fun addParticipants() {
        state.value.currentSearchResult?.let { participantUi ->
            val isAlreadyPartOfUi = state.value.selectedParticipants.any {
                it.id == participantUi.id
            }
            if (isAlreadyPartOfUi.not()) {
                _state.update {
                    it.copy(
                        selectedParticipants = it.selectedParticipants + participantUi,
                        currentSearchResult = null,
                        queryTexState = TextFieldState(),
                        canAddParticipant = false,
                        searchError = null
                    )
                }
                _state.value.queryTexState.clearText()
            }

        }

    }

}