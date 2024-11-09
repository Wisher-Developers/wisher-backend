package ru.itmo.wisher.api.wishes.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.Wishlist
import ru.itmo.wisher.api.wishes.infrastructure.entity.WishlistEntity

@Component
class WishlistCodec(
    private val itemCodec: ItemCodec,
) {

    fun encode(domain: Wishlist) =
        WishlistEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            accessLink = domain.accessLink,
            privateMode = domain.privateMode,
            position = domain.position,
            ownerId = domain.ownerId,
            items = domain.items.map { itemCodec.encode(it) }.toMutableList(),
        )

    fun decode(entity: WishlistEntity) =
        Wishlist(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            accessLink = entity.accessLink,
            privateMode = entity.privateMode,
            position = entity.position,
            ownerId = entity.ownerId,
            items = entity.items.map { itemCodec.decode(it) }.toMutableList(),
        )
}
