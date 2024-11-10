package ru.itmo.wisher.api.wishes.domain.exception

import java.util.UUID

data class WishlistException(
    val id: UUID,
) : RuntimeException("Wishlist with id $id not found")
