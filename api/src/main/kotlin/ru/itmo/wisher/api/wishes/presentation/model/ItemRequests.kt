package ru.itmo.wisher.api.wishes.presentation.model

import java.util.UUID

data class CreateItemRequestDto(
    val name: String,
    val link: String? = null,
    val priority: Int? = null,
    val price: Int? = null,
    val picture: String? = null,
    val description: String? = null,
    val wishlistId: UUID,
)

data class CopyItemRequestDto(
    val oldId: UUID,
    val name: String,
    val link: String? = null,
    val priority: Int? = null,
    val price: Int? = null,
    val picture: String? = null,
    val description: String? = null,
    val wishlistId: UUID,
)

data class UpdateItemRequestDto(
    val id: UUID,
    val name: String? = null,
    val link: String? = null,
    val priority: Int? = null,
    val price: Int? = null,
    val picture: String? = null,
    val description: String? = null,
)
