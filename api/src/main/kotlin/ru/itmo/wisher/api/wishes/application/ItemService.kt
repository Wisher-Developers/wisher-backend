package ru.itmo.wisher.api.wishes.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.CopyItemRequest
import ru.itmo.wisher.api.wishes.domain.CreateItemRequest
import ru.itmo.wisher.api.wishes.domain.Item
import java.util.UUID

@Component
class ItemService(
    private val itemRepository: ItemRepository,
) {

    suspend fun create(request: CreateItemRequest) {
        val item =
            Item(
                id = UUID.randomUUID(),
                name = request.name,
                link = request.link,
                priority = request.priority,
                price = request.price,
                picture = request.picture,
                description = request.description,
                wishlistId = request.wishlistId,
                position = request.position,
                idempotencyId = UUID.randomUUID(),
            )

        itemRepository.save(item)
    }

    suspend fun copy(request: CopyItemRequest) {
        val oldItem = getById(request.oldId)
        val item =
            Item(
                id = UUID.randomUUID(),
                name = request.name,
                link = request.link,
                priority = request.priority,
                price = request.price,
                picture = request.picture,
                description = request.description,
                wishlistId = request.wishlistId,
                position = request.position,
                idempotencyId = oldItem.idempotencyId,
            )

        itemRepository.save(item)
    }

    suspend fun getById(id: UUID): Item {
        return itemRepository.getById(id)
    }
}
