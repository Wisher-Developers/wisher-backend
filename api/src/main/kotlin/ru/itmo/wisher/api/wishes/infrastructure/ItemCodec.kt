package ru.itmo.wisher.api.wishes.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.Item
import ru.itmo.wisher.api.wishes.infrastructure.entity.ItemEntity

@Component
class ItemCodec {

    fun encode(domain: Item) =
        ItemEntity(
            id = domain.id,
            name = domain.name,
            link = domain.link,
            priority = domain.priority,
            price = domain.price,
            picture = domain.picture,
            description = domain.description,
            wishlistId = domain.wishlistId,
            position = domain.position,
            idempotencyId = domain.idempotencyId,
        )

    fun decode(entity: ItemEntity) =
        Item(
            id = entity.id,
            name = entity.name,
            link = entity.link,
            priority = entity.priority,
            price = entity.price,
            picture = entity.picture,
            description = entity.description,
            wishlistId = entity.wishlistId,
            position = entity.position,
            idempotencyId = entity.idempotencyId,
        )
}
