/**
Created by ajinkya on 15/10/25
 */

package com.plcoding.auth.presentation.email_verification

data class EmailVerificationScreenState(
    val isVerifying: Boolean = false,
    val isVerified: Boolean = false,
)