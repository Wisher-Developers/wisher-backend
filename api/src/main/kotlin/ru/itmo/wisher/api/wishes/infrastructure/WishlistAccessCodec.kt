package ru.itmo.wisher.api.wishes.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.infrastructure.UserCodec
import ru.itmo.wisher.api.wishes.domain.WishlistAccess
import ru.itmo.wisher.api.wishes.infrastructure.entity.WishlistAccessEntity
import ru.itmo.wisher.api.wishes.infrastructure.entity.WishlistAccessId

@Component
class WishlistAccessCodec(
    private val wishlistCodec: WishlistCodec,
    private val userCodec: UserCodec,
) {

    fun encode(domain: WishlistAccess) =
        WishlistAccessEntity(
            id =
                WishlistAccessId(
                    wishlistId = domain.wishlist.id,
                    userId = domain.user.id,
                ),
            wishlist = wishlistCodec.encode(domain.wishlist),
            user = userCodec.encode(domain.user),
        )

    fun decode(entity: WishlistAccessEntity) =
        WishlistAccess(
            wishlist = wishlistCodec.decode(entity.wishlist),
            user = userCodec.decode(entity.user),
        )
}
