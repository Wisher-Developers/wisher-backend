package ru.itmo.wisher.api.auth.presentation

import ru.itmo.wisher.api.auth.domain.JwtToken

data class AuthResponse(
    val token: JwtToken,
)
