package ru.itmo.wisher.api.wishes.domain

import java.util.UUID

data class Wishlist(
    var id: UUID,
    var name: String,
    var description: String? = null,
    var accessLink: String? = null,
    var privateMode: PrivateMode,
    var position: Int,
    var ownerId: UUID,
    var items: MutableList<Item>,
)
