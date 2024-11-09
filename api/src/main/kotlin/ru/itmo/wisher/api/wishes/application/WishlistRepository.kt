package ru.itmo.wisher.api.wishes.application

import ru.itmo.wisher.api.wishes.domain.Wishlist
import ru.itmo.wisher.api.wishes.domain.exception.WishlistException
import java.util.UUID

interface WishlistRepository {

    suspend fun save(wishlist: Wishlist): Wishlist

    suspend fun findById(id: UUID): Wishlist?

    suspend fun findByOwnerId(id: UUID): List<Wishlist>

    suspend fun getById(id: UUID): Wishlist {
        return findById(id)
            ?: throw WishlistException(id)
    }
}
