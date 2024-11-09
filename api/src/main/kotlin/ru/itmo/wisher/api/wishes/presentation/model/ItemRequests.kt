package ru.itmo.wisher.api.wishes.presentation.model

import java.util.UUID

data class CreateItemRequestDto(
    val name: String,
    var link: String? = null,
    var priority: Int? = null,
    var price: Int? = null,
    var picture: String? = null,
    var description: String? = null,
    var wishlistId: UUID,
    var position: Int,
)

data class CopyItemRequestDto(
    val oldId: UUID,
    val name: String,
    var link: String? = null,
    var priority: Int? = null,
    var price: Int? = null,
    var picture: String? = null,
    var description: String? = null,
    var wishlistId: UUID,
    var position: Int,
)
