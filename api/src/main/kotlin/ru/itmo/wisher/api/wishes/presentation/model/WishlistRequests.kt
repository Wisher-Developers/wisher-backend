package ru.itmo.wisher.api.wishes.presentation.model

import ru.itmo.wisher.api.wishes.domain.PrivateMode
import java.util.UUID

data class CreateWishlistRequestDto(
    var name: String,
    var description: String? = null,
    var accessLink: String? = null,
    var privateMode: PrivateMode = PrivateMode.PUBLIC,
)

data class UpdateWishlistRequestDto(
    var id: UUID,
    var name: String? = null,
    var description: String? = null,
    var privateMode: PrivateMode? = null,
)
