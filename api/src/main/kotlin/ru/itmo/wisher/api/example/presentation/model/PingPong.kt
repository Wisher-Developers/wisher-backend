package ru.itmo.wisher.api.example.presentation.model

import java.util.UUID

data class PingPong(
    val id: UUID,
    val value: String
)