package ru.itmo.wisher.api.wishes.presentation.model

import ru.itmo.wisher.api.wishes.domain.PrivateMode
import java.util.UUID

data class WishlistResponse(
    val id: UUID,
    val name: String,
    val description: String?,
    val accessLink: String?,
    val privateMode: PrivateMode,
    val position: Int,
    val ownerId: UUID,
    val items: List<ItemResponse>,
)
