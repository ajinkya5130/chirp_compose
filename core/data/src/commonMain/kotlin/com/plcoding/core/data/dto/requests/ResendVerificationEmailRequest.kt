package com.plcoding.core.data.dto.requests

import kotlinx.serialization.Serializable

@Serializable
data class ResendVerificationEmailRequest(val email: String)
