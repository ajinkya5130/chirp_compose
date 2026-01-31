/**
Created by ajinkya on 25/10/25
 */

package com.plcoding.auth.presentation.forgot_pass

import androidx.compose.foundation.text.input.TextFieldState
import com.plcoding.core.presentation.utils.UiText

data class ForgotPassScreenState(
    val emailTextFieldState: TextFieldState = TextFieldState(),
    val errorText: UiText? = null,
    val emailSendSuccessText: UiText? = null,
    val canSubmit: Boolean = false,
    val isLoading: Boolean = false,
    val isEmailSendSuccessfully: Boolean = false,
)