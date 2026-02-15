/**
Created by ajinkyak on 14/02/26
 */

package com.plcoding.chat.presentation.createChat

/**
 * Sealed interface representing all possible user actions on the Create Chat screen.
 *
 * This interface defines the actions that users can perform while creating a new chat,
 * including adding participants, creating the chat, and dismissing the dialog.
 */
sealed interface CreateChatScreenAction {
    /**
     * Action triggered when the user clicks the "Add" button to add a participant to the chat.
     */
    data object onAddClick : CreateChatScreenAction

    /**
     * Action triggered when the user dismisses or closes the create chat dialog.
     */
    data object onDismissDialog : CreateChatScreenAction

    /**
     * Action triggered when the user clicks the "Create Chat" button to finalize chat creation.
     */
    data object OnCreateChatClick : CreateChatScreenAction

}