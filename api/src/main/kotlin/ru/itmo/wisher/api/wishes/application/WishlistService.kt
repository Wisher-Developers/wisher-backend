package ru.itmo.wisher.api.wishes.application

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.User
import ru.itmo.wisher.api.wishes.domain.CreateWishlistRequest
import ru.itmo.wisher.api.wishes.domain.Wishlist
import java.util.UUID

@Component
class WishlistService(
    private val wishlistRepository: WishlistRepository,
) {

    suspend fun create(request: CreateWishlistRequest) {
        val user = SecurityContextHolder.getContext().authentication.principal as User
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

        wishlistRepository.save(wishlist)
    }

    suspend fun getByOwnerId(id: UUID): List<Wishlist> {
        return wishlistRepository.findByOwnerId(id)
    }

    suspend fun getById(id: UUID): Wishlist {
        return wishlistRepository.getById(id)
    }
}
