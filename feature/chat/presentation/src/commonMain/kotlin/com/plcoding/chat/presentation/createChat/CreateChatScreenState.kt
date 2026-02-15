/**
Created by ajinkyak on 14/02/26
 */

package com.plcoding.chat.presentation.createChat

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.chat.models.ChatParticipant
import com.plcoding.core.presentation.utils.UiText

/**
 * Data class representing the UI state for the Create Chat screen.
 *
 * This state holds all the information needed to render the create chat interface,
 * including search query, selected participants, loading states, and error messages.
 *
 * @property queryTexState The text field state for the participant search query input.
 * @property selectedParticipants List of participants that have been selected for the chat.
 * @property isAddingParticipants Flag indicating whether a participant is currently being added.
 * @property isLoadingParticipants Flag indicating whether participant search is in progress.
 * @property canAddParticipant Flag indicating whether the current search query allows adding a participant.
 * @property currentSearchResult The current participant search result, if any.
 * @property searchError Error message to display if participant search fails.
 * @property isCretingChat Flag indicating whether the chat creation process is in progress.
 */
data class CreateChatScreenState(
    val queryTexState: TextFieldState = TextFieldState(),
    val selectedParticipants: List<ChatParticipant> = emptyList(),
    val isAddingParticipants: Boolean = false,
    val isLoadingParticipants: Boolean = false,
    val canAddParticipant: Boolean = false,
    val currentSearchResult: ChatParticipant? = null,
    val searchError: UiText? = null,
    val isCretingChat: Boolean = false,

    )