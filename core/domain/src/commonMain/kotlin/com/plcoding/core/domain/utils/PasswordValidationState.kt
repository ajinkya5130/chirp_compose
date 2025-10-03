package com.plcoding.core.domain.utils

data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasDigit: Boolean = false,
    val hasUpperCase: Boolean,
) {
    val isValidPassword: Boolean
        get() = hasMinLength && hasDigit && hasUpperCase
}
