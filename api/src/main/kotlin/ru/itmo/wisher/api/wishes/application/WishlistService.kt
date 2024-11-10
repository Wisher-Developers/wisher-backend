package ru.itmo.wisher.api.wishes.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.domain.User
import ru.itmo.wisher.api.wishes.domain.CreateWishlistRequest
import ru.itmo.wisher.api.wishes.domain.UpdateWishlistRequest
import ru.itmo.wisher.api.wishes.domain.Wishlist
import java.util.UUID

@Component
class WishlistService(
    private val wishlistRepository: WishlistRepository,
    private val itemRepository: WishlistRepository,
) {

    fun create(request: CreateWishlistRequest): Wishlist {
        val user = User.current()
        val wishlist =
            Wishlist(
                id = UUID.randomUUID(),
                name = request.name,
                description = request.description,
                ownerId = user.id,
                privateMode = request.privateMode,
                position = getByOwnerId(user.id).size + 1,
                items = mutableListOf(),
            )

        return wishlistRepository.save(wishlist)
    }

    fun getByOwnerId(id: UUID): List<Wishlist> {
        return wishlistRepository.findByOwnerId(id)
    }

    fun getById(id: UUID): Wishlist {
        return wishlistRepository.getById(id)
    }

    fun delete(id: UUID) {
        getById(id).items.forEach { itemRepository.deleteById(it.id) }
        wishlistRepository.deleteById(id)
    }

    fun update(request: UpdateWishlistRequest): Wishlist {
        val wishlist = getById(request.id)
        if (request.name != null)
            wishlist.name = request.name!!

        if (request.description != null)
            wishlist.description = request.description!!

        if (request.privateMode != null)
            wishlist.privateMode = request.privateMode!!

        return wishlistRepository.save(wishlist)
    }
}
