package ru.itmo.wisher.api.wishes.domain

import java.util.UUID

data class CreateWishlistRequest(
    var name: String,
    var description: String? = null,
    var accessLink: String? = null,
    var privateMode: PrivateMode = PrivateMode.PUBLIC,
    var ownerId: UUID,
)

data class UpdateWishlistRequest(
    var id: UUID,
    var name: String? = null,
    var description: String? = null,
    var privateMode: PrivateMode? = null,
)
