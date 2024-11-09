package ru.itmo.wisher.api.user.domain.exception

import java.util.UUID

data class NoSuchUsername(
    val username: String,
) : RuntimeException("User with username $username not found")

data class NoSuchUserIdException(
    val id: UUID,
) : RuntimeException("User with id $id not found")
