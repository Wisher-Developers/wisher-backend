package ru.itmo.wisher.api.wishes.application

import ru.itmo.wisher.api.wishes.domain.Wishlist
import ru.itmo.wisher.api.wishes.domain.exception.WishlistException
import java.util.UUID

interface WishlistRepository {

    fun save(wishlist: Wishlist): Wishlist

    fun findById(id: UUID): Wishlist?

    fun deleteById(id: UUID)

    fun findByOwnerId(id: UUID): List<Wishlist>

    fun getById(id: UUID): Wishlist {
        return findById(id)
            ?: throw WishlistException(id)
    }
}
