package ru.itmo.wisher.api.wishes.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.WishlistAccess
import ru.itmo.wisher.api.wishes.infrastructure.entity.WishlistAccessJpaRepository
import java.util.UUID
import ru.itmo.wisher.api.wishes.application.AccessRepository as IWishlistAccessRepository

@Component
class WishlistAccessRepository(
    private val wishlistAccessCodec: WishlistAccessCodec,
    private val wishlistAccessJpaRepository: WishlistAccessJpaRepository,
) : IWishlistAccessRepository {

    override fun save(wishlistAccess: WishlistAccess): WishlistAccess {
        return wishlistAccessCodec.encode(wishlistAccess)
            .let { wishlistAccessJpaRepository.save(it) }
            .let { wishlistAccessCodec.decode(it) }
    }

    override fun findByWishlistId(id: UUID): List<WishlistAccess> {
        return wishlistAccessJpaRepository
            .findByWishlistId(id)
            .let { it.map { w -> wishlistAccessCodec.decode(w) } }
    }

    override fun delete(wishlistAccess: WishlistAccess) {
        wishlistAccessJpaRepository.delete(wishlistAccessCodec.encode(wishlistAccess))
    }
}
