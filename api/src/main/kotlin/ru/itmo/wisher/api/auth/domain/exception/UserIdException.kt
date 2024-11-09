package ru.itmo.wisher.api.auth.domain.exception

import java.util.UUID

data class UserIdException(
    val id: UUID,
) : RuntimeException("User with id $id not found")
