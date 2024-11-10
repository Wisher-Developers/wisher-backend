package ru.itmo.wisher.api.wishes.domain

import java.util.UUID

data class CreateItemRequest(
    val name: String,
    var link: String? = null,
    var priority: Int? = null,
    var price: Int? = null,
    var picture: String? = null,
    var description: String? = null,
    var wishlistId: UUID,
)

data class CopyItemRequest(
    val oldId: UUID,
    val name: String,
    var link: String? = null,
    var priority: Int? = null,
    var price: Int? = null,
    var picture: String? = null,
    var description: String? = null,
    var wishlistId: UUID,
)
