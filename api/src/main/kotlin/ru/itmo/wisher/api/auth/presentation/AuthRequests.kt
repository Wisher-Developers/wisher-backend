package ru.itmo.wisher.api.auth.presentation

data class SignUpRequestDto(
    val username: String,
    val password: String,
    val email: String,
)

data class LogInRequestDto(
    val username: String,
    val password: String,
)
