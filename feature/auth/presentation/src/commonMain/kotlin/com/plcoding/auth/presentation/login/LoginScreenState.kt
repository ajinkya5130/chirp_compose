/**
Created by ajinkya on 18/10/25
 */

package com.plcoding.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.utils.UiText

/**
 * UI state for the login screen.
 *
 * Contains all the state needed to render the login screen and handle user interactions.
 *
 * @property emailTextFieldState The state of the email text field
 * @property passwordTextFieldState The state of the password text field
 * @property isPasswordVisible Whether the password is currently visible (not masked)
 * @property loginError Optional error message to display if login fails
 * @property isLoggingIn Whether a login request is currently in progress
 * @property canLogin Whether the login button should be enabled (valid inputs)
 */
data class LoginScreenState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val loginError: UiText? = null,
    val isLoggingIn: Boolean = false,
    val canLogin: Boolean = false,
)