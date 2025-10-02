/**
Created by ajinkya on 01/10/25
 */

package com.plcoding.auth.presentation.register

sealed interface RegisterScreenAction {
    data object OnLoginClick : RegisterScreenAction
    data object OnInputTextFocusGain : RegisterScreenAction
    data object OnRegisterClick : RegisterScreenAction
    data object OnTogglePasswordVisibilityClick : RegisterScreenAction

}