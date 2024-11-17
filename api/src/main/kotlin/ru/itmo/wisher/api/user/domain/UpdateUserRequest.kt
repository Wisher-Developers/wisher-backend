package ru.itmo.wisher.api.user.domain

import java.util.UUID

data class UpdateUserRequest(
    val id: UUID,
    val email: String? = null,
    val username: String? = null,
    val password: String? = null,
    val avatar: String? = null,
)
