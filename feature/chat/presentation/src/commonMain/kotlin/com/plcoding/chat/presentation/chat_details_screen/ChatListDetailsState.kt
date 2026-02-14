/**
Created by ajinkyak on 13/02/26
 */

package com.plcoding.chat.presentation.chat_details_screen

/**
 * Data class representing the state of the chat list details screen.
 *
 * @property selectedChatId The ID of the currently selected chat, or null if no chat is selected
 * @property dialogState The current state of any displayed dialog
 */
data class ChatListDetailsState(
    val selectedChatId: String? = null,
    val dialogState: DialogState = DialogState.Hidden,
)

/**
 * Sealed interface representing the possible states of dialogs in the chat list details screen.
 */
sealed interface DialogState {
    /**
     * No dialog is currently displayed.
     */
    data object Hidden : DialogState

    /**
     * The create chat dialog is displayed.
     */
    data object CreateChat : DialogState

    /**
     * The user profile dialog is displayed.
     */
    data object Profile : DialogState

    /**
     * The manage chat dialog is displayed for a specific chat.
     *
     * @property chatId The unique identifier of the chat being managed
     */
    data class ManageChat(val chatId: String) : DialogState
}