package ru.itmo.wisher.api.wishes.domain

import java.util.UUID

data class Item(
    val id: UUID,
    var name: String,
    var link: String? = null,
    var priority: Int? = null,
    var price: Int? = null,
    var picture: String? = null,
    var description: String? = null,
    var wishlistId: UUID,
    var position: Int,
    var idempotencyId: UUID,
)
