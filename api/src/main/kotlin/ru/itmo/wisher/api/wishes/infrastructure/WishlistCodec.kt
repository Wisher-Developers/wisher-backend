package ru.itmo.wisher.api.wishes.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.infrastructure.UserCodec
import ru.itmo.wisher.api.wishes.domain.Wishlist
import ru.itmo.wisher.api.wishes.infrastructure.entity.WishlistEntity

@Component
class WishlistCodec(
    private val itemCodec: ItemCodec,
    private val userCodec: UserCodec,
) {

    fun encode(domain: Wishlist) =
        WishlistEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            accessLink = domain.accessLink,
            privateMode = domain.privateMode,
            position = domain.position,
            owner = userCodec.encode(domain.owner),
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
            owner = userCodec.decode(entity.owner),
            items = entity.items.map { itemCodec.decode(it) }.toMutableList(),
        )
}
