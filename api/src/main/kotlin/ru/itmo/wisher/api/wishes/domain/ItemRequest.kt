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
    val link: String? = null,
    val priority: Int? = null,
    val price: Int? = null,
    val picture: String? = null,
    val description: String? = null,
    val wishlistId: UUID,
)

data class UpdateItemRequest(
    val id: UUID,
    val name: String? = null,
    val link: String? = null,
    val priority: Int? = null,
    val price: Int? = null,
    val picture: String? = null,
    val description: String? = null,
)
