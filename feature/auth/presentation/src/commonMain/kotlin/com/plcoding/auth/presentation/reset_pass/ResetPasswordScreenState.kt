/**
Created by ajinkya on 26/10/25
 */

package com.plcoding.auth.presentation.reset_pass

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.utils.UiText

data class ResetPasswordScreenState(
    val passwordField: TextFieldState = TextFieldState(),
    val isLoading: Boolean = false,
    val errorText: UiText? = null,
    val canSubmit: Boolean = false,
    val passwordToggle: Boolean = false,
    val isPasswordResetSuccessfully: Boolean = false,

    )