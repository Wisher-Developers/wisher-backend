package ru.itmo.wisher.api.wishes.application

import ru.itmo.wisher.api.wishes.domain.WishlistAccess
import java.util.UUID

interface AccessRepository {

    fun save(wishlistAccess: WishlistAccess): WishlistAccess

    fun findByWishlistId(id: UUID): List<WishlistAccess>

    fun delete(wishlistAccess: WishlistAccess)
}
