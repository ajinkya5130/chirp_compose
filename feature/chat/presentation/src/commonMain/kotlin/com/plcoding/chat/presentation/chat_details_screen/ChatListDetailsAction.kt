/**
Created by ajinkyak on 13/02/26
 */

package com.plcoding.chat.presentation.chat_details_screen

/**
 * Sealed interface representing all possible user actions in the chat list details screen.
 *
 * This interface defines the actions that users can perform in the adaptive layout
 * containing both the chat list and chat details.
 */
sealed interface ChatListDetailsAction {
    /**
     * Action triggered when the user clicks the create chat button.
     */
    data object OnCreateChatClick : ChatListDetailsAction

    /**
     * Action triggered when the user clicks the profile button.
     */
    data object OnProfileClick : ChatListDetailsAction

    /**
     * Action triggered when the user clicks to manage a specific chat.
     *
     * @property chatId The unique identifier of the chat to manage
     */
    data class OnManageChatClick(val chatId: String) : ChatListDetailsAction

    /**
     * Action triggered when the user clicks on a chat item in the list.
     *
     * @property chatId The unique identifier of the selected chat
     */
    data class OnChatClick(val chatId: String) : ChatListDetailsAction

    /**
     * Action triggered when the user dismisses a dialog.
     */
    data object OnDismissDialog : ChatListDetailsAction

}