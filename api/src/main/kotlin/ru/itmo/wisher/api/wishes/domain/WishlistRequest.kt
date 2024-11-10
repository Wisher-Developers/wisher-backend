package ru.itmo.wisher.api.wishes.domain

import java.util.UUID

data class CreateWishlistRequest(
    val name: String,
    val description: String? = null,
    val privateMode: PrivateMode = PrivateMode.PUBLIC,
)

data class UpdateWishlistRequest(
    val id: UUID,
    val name: String? = null,
    val description: String? = null,
    val privateMode: PrivateMode? = null,
)
