package com.plcoding.core.domain.validations

/**
 * Password validation state.
 *
 * Contains the results of password validation checks.
 *
 * @property hasMinLength Whether the password meets the minimum length requirement
 * @property hasDigit Whether the password contains at least one digit
 * @property hasUpperCase Whether the password contains at least one uppercase letter
 */
data class PasswordValidationState(
    val hasMinLength: Boolean = false,
    val hasDigit: Boolean = false,
    val hasUpperCase: Boolean,
) {
    /**
     * Indicates whether the password is valid.
     *
     * A password is considered valid if it meets all validation criteria:
     * minimum length, contains a digit, and contains an uppercase letter.
     */
    val isValidPassword: Boolean
        get() = hasMinLength && hasDigit && hasUpperCase
}
