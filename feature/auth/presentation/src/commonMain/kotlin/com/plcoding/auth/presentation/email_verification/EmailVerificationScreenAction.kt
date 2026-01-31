/**
Created by ajinkya on 15/10/25
 */

package com.plcoding.auth.presentation.email_verification

sealed interface EmailVerificationScreenAction {
    data object OnLoginClick : EmailVerificationScreenAction
    data object OnCloseClick : EmailVerificationScreenAction

}