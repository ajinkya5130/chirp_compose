/**
Created by ajinkya on 01/10/25
 */

package com.plcoding.auth.presentation.register

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.utils.UiText

data class RegisterScreenState(
    val emailTextState: TextFieldState = TextFieldState(),
    val isEmailValid: Boolean = false,
    val emailError: UiText? = null,
    val passwordTextState: TextFieldState = TextFieldState(),
    val isPasswordValid: Boolean = false,
    val passwordError: UiText? = null,
    val userNameTextState: TextFieldState = TextFieldState(),
    val isUserNameValid: Boolean = false,
    val userNameError: UiText? = null,
    val registraionError: UiText? = null,
    val isRegistering: Boolean = false,
    val canRegister: Boolean = false,
    val isPasswordVisible: Boolean = false,
)