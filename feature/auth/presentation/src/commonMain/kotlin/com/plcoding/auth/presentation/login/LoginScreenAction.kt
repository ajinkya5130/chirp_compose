/**
Created by ajinkya on 18/10/25
 */

package com.plcoding.auth.presentation.login

/**
 * Sealed interface representing user actions on the login screen.
 *
 * These actions are dispatched from the UI to the ViewModel for processing.
 */
sealed interface LoginScreenAction {

    /** User clicked the login button */
    data object OnLoginClick : LoginScreenAction

    /** User clicked the register button to navigate to registration */
    data object OnRegisterClick : LoginScreenAction

    /** User clicked the forgot password button */
    data object OnForgotPasswordClick : LoginScreenAction

    /** User toggled password visibility */
    data object OnTogglePasswordVisibilityClick : LoginScreenAction

    /** Login was successful */
    data object OnLoginSuccess : LoginScreenAction

}