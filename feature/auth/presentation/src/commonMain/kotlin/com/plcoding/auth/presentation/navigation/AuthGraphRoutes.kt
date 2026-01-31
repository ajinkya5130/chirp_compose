package com.plcoding.auth.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface AuthGraphRoutes {

    @Serializable
    data object Graph : AuthGraphRoutes

    @Serializable
    data object Login : AuthGraphRoutes

    @Serializable
    data object Register : AuthGraphRoutes

    @Serializable
    data class RegisterSuccess(val email: String) :
        AuthGraphRoutes //this email key must be match with KEY_EMAIL_STRING

    @Serializable
    data object ForgotPassword : AuthGraphRoutes

    @Serializable
    data class ResetPassword(val token: String) : AuthGraphRoutes

    @Serializable
    data class EmailVerification(val token: String) : AuthGraphRoutes


}