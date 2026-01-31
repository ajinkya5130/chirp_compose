package com.plcoding.auth.presentation.login

/**
 * Sealed interface representing one-time events from the login screen.
 *
 * These events are emitted from the ViewModel to the UI for handling side effects
 * like navigation or showing messages.
 */
sealed interface LoginEvent {
    /** Login was successful, navigate to the main screen */
    data object OnLoginSuccess : LoginEvent

    /** Email is not verified, show verification prompt */
    data object OnEmailNotVerified : LoginEvent

    /**
     * Verification email was resent successfully.
     *
     * @property email The email address where the verification was sent
     */
    data class OnEmailResentSuccess(val email: String) : LoginEvent
}