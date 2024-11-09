package ru.itmo.wisher.api.wishes.infrastructure

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.Wishlist
import ru.itmo.wisher.api.wishes.infrastructure.entity.WishlistJpaRepository
import java.util.UUID
import ru.itmo.wisher.api.wishes.application.WishlistRepository as IWishlistRepository

@Component
class WishlistRepository(
    private val wishlistCodec: WishlistCodec,
    private val wishlistJpaRepository: WishlistJpaRepository,
) : IWishlistRepository {
    override fun save(wishlist: Wishlist): Wishlist {
        return wishlistCodec.encode(wishlist)
            .let { wishlistJpaRepository.save(it) }
            .let { wishlistCodec.decode(it) }
    }

    override fun findById(id: UUID): Wishlist? {
        return wishlistJpaRepository
            .findByIdOrNull(id)
            ?.let { wishlistCodec.decode(it) }
    }

    override fun findByOwnerId(id: UUID): List<Wishlist> {
        return wishlistJpaRepository
            .findByOwnerId(id)
            .let { it.map { w -> wishlistCodec.decode(w) } }
    }
}
