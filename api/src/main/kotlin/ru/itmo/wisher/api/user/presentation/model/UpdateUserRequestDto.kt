package ru.itmo.wisher.api.user.presentation.model

import java.util.UUID

data class UpdateUserRequestDto(
    val id: UUID,
    val email: String? = null,
    val username: String? = null,
    val password: String? = null,
    val avatar: String? = null,
)
