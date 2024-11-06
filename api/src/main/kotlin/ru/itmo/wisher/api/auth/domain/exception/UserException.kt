package ru.itmo.wisher.api.auth.domain.exception

data class UserException(
    val username: String,
) : RuntimeException("User with username $username not found")
