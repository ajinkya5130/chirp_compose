/**
Created by ajinkya on 11/10/25
 */

package com.plcoding.auth.presentation.register.register_success

import com.plcoding.core.presentation.utils.UiText
import kotlinx.serialization.Serializable

@Serializable
data class RegisterSuccessState(
    val email: String = "",
    val isResendEnabled: Boolean = false,
    val resnetErrorText: UiText? = null,
)