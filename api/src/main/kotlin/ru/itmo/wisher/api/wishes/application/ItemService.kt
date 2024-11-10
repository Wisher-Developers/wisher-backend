package ru.itmo.wisher.api.wishes.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.CopyItemRequest
import ru.itmo.wisher.api.wishes.domain.CreateItemRequest
import ru.itmo.wisher.api.wishes.domain.Item
import java.util.UUID

@Component
class ItemService(
    private val itemRepository: ItemRepository,
    private val wishlistRepository: WishlistRepository,
) {

    fun create(request: CreateItemRequest) {
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
                position = wishlistRepository.getById(request.wishlistId).items.size + 1,
                idempotencyId = UUID.randomUUID(),
            )

        itemRepository.save(item)
    }

    fun copy(request: CopyItemRequest) {
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
                position = wishlistRepository.getById(request.wishlistId).items.size + 1,
                idempotencyId = oldItem.idempotencyId,
            )

        itemRepository.save(item)
    }

    fun delete(id: UUID) {
        val item = itemRepository.getById(id)
        val items = itemRepository.findByWishlistId(item.wishlistId)
        items.filter { it.position > item.position }
            .map {
                it.position -= 1
                itemRepository.save(it)
            }
        itemRepository.deleteById(id)
    }

    fun getById(id: UUID): Item {
        return itemRepository.getById(id)
    }
}
