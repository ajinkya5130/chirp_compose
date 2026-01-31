/**
Created by ajinkya on 11/10/25
 */

package com.plcoding.auth.presentation.register.register_success

sealed interface RegisterSuccessAction {
    data object OnLoginClick : RegisterSuccessAction
    data object OnResendClick : RegisterSuccessAction

}