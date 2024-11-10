package ru.itmo.wisher.api.wishes.presentation

import java.util.UUID

data class ItemResponse(
    val id: UUID,
    val name: String,
    val link: String?,
    val priority: Int?,
    val price: Int?,
    val picture: String?,
    val description: String?,
    val wishlistId: UUID,
    val position: Int,
)
