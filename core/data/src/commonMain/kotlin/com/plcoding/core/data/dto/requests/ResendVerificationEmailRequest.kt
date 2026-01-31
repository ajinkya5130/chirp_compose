package com.plcoding.core.data.dto.requests

import kotlinx.serialization.Serializable

/**
 * Request data for resending email verification.
 *
 * This serializable class represents the data required to request a new verification email.
 *
 * @property email The email address to send the verification link to
 */
@Serializable
data class ResendVerificationEmailRequest(val email: String)
