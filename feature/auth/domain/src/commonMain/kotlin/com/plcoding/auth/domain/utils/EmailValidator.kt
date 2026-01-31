package com.plcoding.auth.domain.utils

/**
 * Email validation utility.
 *
 * Provides email address validation using a regular expression pattern.
 */
object EmailValidator {

    //private const val EMAIL_PATTERN = "[a-zA-Z0-9+._%\\-]{1,256}" + "@" + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+"
    private const val EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"

    /**
     * Validates an email address format.
     *
     * Checks if the provided email string matches the standard email format pattern.
     *
     * @param email The email address string to validate
     * @return true if the email format is valid, false otherwise
     */
    fun validate(email: String): Boolean {
        return EMAIL_PATTERN.toRegex().matches(email)
    }
}