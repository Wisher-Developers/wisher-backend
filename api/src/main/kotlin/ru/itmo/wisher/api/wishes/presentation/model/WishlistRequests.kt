package ru.itmo.wisher.api.wishes.presentation.model

import ru.itmo.wisher.api.wishes.domain.PrivateMode
import java.util.UUID

data class CreateWishlistRequestDto(
    val name: String,
    val description: String? = null,
    val privateMode: PrivateMode = PrivateMode.PUBLIC,
)

data class UpdateWishlistRequestDto(
    val id: UUID,
    val name: String? = null,
    val description: String? = null,
    val privateMode: PrivateMode? = null,
)
