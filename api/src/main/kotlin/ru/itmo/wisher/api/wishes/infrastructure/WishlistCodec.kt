package ru.itmo.wisher.api.wishes.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.Wishlist
import ru.itmo.wisher.api.wishes.infrastructure.entity.WishlistEntity

@Component
class WishlistCodec {

    fun encode(domain: Wishlist) =
        WishlistEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            accessLink = domain.accessLink,
            privateMode = domain.privateMode,
            position = domain.position,
            owner = domain.owner,
            items = domain.items,
        )

    fun decode(entity: WishlistEntity) =
        Wishlist(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            accessLink = entity.accessLink,
            privateMode = entity.privateMode,
            position = entity.position,
            owner = entity.owner,
            items = entity.items,
        )
}
