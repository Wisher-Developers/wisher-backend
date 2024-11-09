package ru.itmo.wisher.api.auth.presentation.model

import ru.itmo.wisher.api.auth.domain.JwtToken

data class AuthResponse(
    val token: JwtToken,
)
