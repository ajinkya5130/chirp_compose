package com.plcoding.auth.presentation.login

sealed interface LoginEvent {
    data object OnLoginSuccess : LoginEvent
    data object OnEmailNotVerified : LoginEvent
    data class OnEmailResentSuccess(val email: String) : LoginEvent
}