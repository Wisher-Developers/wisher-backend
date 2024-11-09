package ru.itmo.wisher.api.auth.domain

data class SignUpRequest(
    val username: String,
    val password: String,
    val email: String,
)

data class LogInRequest(
    val username: String,
    val password: String,
)
