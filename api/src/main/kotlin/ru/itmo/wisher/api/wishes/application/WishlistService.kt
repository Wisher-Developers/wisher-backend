package ru.itmo.wisher.api.wishes.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.application.UserRepository
import ru.itmo.wisher.api.wishes.domain.CreateWishlistRequest
import ru.itmo.wisher.api.wishes.domain.Wishlist
import java.util.UUID

@Component
class WishlistService(
    private val wishlistRepository: WishlistRepository,
    private val userRepository: UserRepository,
) {

    suspend fun create(request: CreateWishlistRequest) {
        val wishlist =
            Wishlist(
                id = UUID.randomUUID(),
                name = request.name,
                description = request.description,
                owner = userRepository.getById(request.ownerId),
                privateMode = request.privateMode,
                position = getByOwnerId(request.ownerId).size + 1,
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
