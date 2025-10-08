package com.plcoding.core.domain.validations

object PasswordValidator {

    private const val PASSWORD_MIN_LENGTH = 9
    fun validate(password: String): PasswordValidationState {
        return PasswordValidationState(
            hasMinLength = password.length >= PASSWORD_MIN_LENGTH,
            hasDigit = password.any { it.isDigit() },
            hasUpperCase = password.any { it.isUpperCase() }
        )
    }
}