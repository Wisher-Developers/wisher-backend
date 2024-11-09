package ru.itmo.wisher.api.user.presentation.model

import java.util.UUID

data class User(
    val id: UUID,
    val username: String,
    val email: String,
    val avatar: String? = null,
)
