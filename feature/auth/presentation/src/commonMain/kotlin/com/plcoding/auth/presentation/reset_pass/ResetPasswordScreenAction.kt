/**
Created by ajinkya on 26/10/25
 */

package com.plcoding.auth.presentation.reset_pass

sealed interface ResetPasswordScreenAction {
    data object OnSubmitClick : ResetPasswordScreenAction
    data object OnPasswordToggle : ResetPasswordScreenAction

}