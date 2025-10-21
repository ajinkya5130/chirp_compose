/**
Created by ajinkya on 18/10/25
 */

package com.plcoding.auth.presentation.login

sealed interface LoginScreenAction {

    data object OnLoginClick : LoginScreenAction
    data object OnRegisterClick : LoginScreenAction
    data object OnForgotPasswordClick : LoginScreenAction
    data object OnTogglePasswordVisibilityClick : LoginScreenAction
    data object OnLoginSuccess : LoginScreenAction

}