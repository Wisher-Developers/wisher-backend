package ru.itmo.wisher.api.wishes.application

import ru.itmo.wisher.api.wishes.domain.Wishlist
import ru.itmo.wisher.api.wishes.domain.exception.WishlistException
import ru.itmo.wisher.api.wishes.domain.exception.WishlistLinkException
import java.util.UUID

interface WishlistRepository {

    fun save(wishlist: Wishlist): Wishlist

    fun findById(id: UUID): Wishlist?

    fun findByAccessLink(accessLink: String): Wishlist?

    fun deleteById(id: UUID)

    fun findByOwnerId(id: UUID): List<Wishlist>

    fun getById(id: UUID): Wishlist {
        return findById(id)
            ?: throw WishlistException(id)
    }

    fun getByAccessLink(accessLink: String): Wishlist {
        return findByAccessLink(accessLink)
            ?: throw WishlistLinkException(accessLink)
    }
}
