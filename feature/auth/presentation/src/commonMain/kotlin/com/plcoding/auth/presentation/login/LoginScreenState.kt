/**
Created by ajinkya on 18/10/25
 */

package com.plcoding.auth.presentation.login

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.utils.UiText

data class LoginScreenState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val passwordTextFieldState: TextFieldState = TextFieldState(),
    val isPasswordVisible: Boolean = false,
    val loginError: UiText? = null,
    val isLoggingIn: Boolean = false,
    val canLogin: Boolean = false,
)