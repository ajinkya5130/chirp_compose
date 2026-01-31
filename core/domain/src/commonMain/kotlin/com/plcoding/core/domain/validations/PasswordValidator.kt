package com.plcoding.core.domain.validations

/**
 * Password validation utility.
 *
 * Provides password strength validation based on common security requirements.
 */
object PasswordValidator {

    private const val PASSWORD_MIN_LENGTH = 9

    /**
     * Validates a password against security requirements.
     *
     * Checks if the password meets the following criteria:
     * - Minimum length of 9 characters
     * - Contains at least one digit
     * - Contains at least one uppercase letter
     *
     * @param password The password string to validate
     * @return [PasswordValidationState] containing validation results for each criterion
     */
    fun validate(password: String): PasswordValidationState {
        return PasswordValidationState(
            hasMinLength = password.length >= PASSWORD_MIN_LENGTH,
            hasDigit = password.any { it.isDigit() },
            hasUpperCase = password.any { it.isUpperCase() }
        )
    }
}