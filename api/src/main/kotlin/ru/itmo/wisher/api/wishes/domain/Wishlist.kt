package ru.itmo.wisher.api.wishes.domain

import ru.itmo.wisher.api.auth.domain.User
import java.util.UUID

data class Wishlist(
    var id: UUID,
    var name: String,
    var description: String? = null,
    var accessLink: String? = null,
    var privateMode: PrivateMode,
    var position: Int,
    var owner: User,
    var items: MutableList<Item>,
)
