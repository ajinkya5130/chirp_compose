/**
Created by ajinkya on 11/10/25
 */

package com.plcoding.auth.presentation.register.register_success

data class RegisterSuccessState(
    val email: String = "",
    val isResendEnabled: Boolean = false,
)