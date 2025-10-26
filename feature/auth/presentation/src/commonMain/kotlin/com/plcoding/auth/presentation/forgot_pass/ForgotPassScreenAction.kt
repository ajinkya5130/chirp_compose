/**
Created by ajinkya on 25/10/25
 */

package com.plcoding.auth.presentation.forgot_pass

sealed interface ForgotPassScreenAction {
    data object OnSubmitClick : ForgotPassScreenAction
}