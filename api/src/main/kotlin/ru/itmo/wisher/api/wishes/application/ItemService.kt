package ru.itmo.wisher.api.wishes.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.domain.User
import ru.itmo.wisher.api.wishes.domain.CopyItemRequest
import ru.itmo.wisher.api.wishes.domain.CreateItemRequest
import ru.itmo.wisher.api.wishes.domain.Item
import ru.itmo.wisher.api.wishes.domain.UpdateItemRequest
import ru.itmo.wisher.api.wishes.domain.exception.UserIsNotOwnerException
import java.util.UUID

@Component
class ItemService(
    private val itemRepository: ItemRepository,
    private val wishlistRepository: WishlistRepository,
) {

    fun create(request: CreateItemRequest): Item {
        val wishlist = wishlistRepository.getById(request.wishlistId)
        if (User.current().id != wishlist.owner.id) {
            throw UserIsNotOwnerException(wishlist.id)
        }

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
                position = wishlist.items.size + 1,
                idempotencyId = UUID.randomUUID(),
            )

        return itemRepository.save(item)
    }

    fun copy(request: CopyItemRequest): Item {
        val wishlist = wishlistRepository.getById(request.wishlistId)
        if (User.current().id != wishlist.owner.id) {
            throw UserIsNotOwnerException(wishlist.id)
        }

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
                position = wishlist.items.size + 1,
                idempotencyId = oldItem.idempotencyId,
            )

        return itemRepository.save(item)
    }

    fun update(request: UpdateItemRequest): Item {
        val item = getById(request.id)
        val wishlist = wishlistRepository.getById(item.wishlistId)
        if (User.current().id != wishlist.owner.id) {
            throw UserIsNotOwnerException(wishlist.id)
        }

        if (request.name != null) {
            item.name = request.name
        }

        if (request.link != null) {
            item.link = request.link
        }

        if (request.description != null) {
            item.description = request.description
        }

        if (request.picture != null) {
            item.picture = request.picture
        }

        if (request.price != null) {
            item.price = request.price
        }

        if (request.priority != null) {
            item.priority = request.priority
        }

        return itemRepository.save(item)
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

    fun getRecommendations(): List<Item> {
        return itemRepository.getRecommendations(User.current().id)
    }
}
