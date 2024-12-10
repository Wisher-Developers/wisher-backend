package ru.itmo.wisher.api.wishes.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.kafka.application.IKafkaProducer
import ru.itmo.wisher.api.kafka.application.KafkaMessageBuilder
import ru.itmo.wisher.api.kafka.infrastructure.NewWishItemMessage
import ru.itmo.wisher.api.kafka.infrastructure.SuggestMatchingWishItemsMessage
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
    private val kafkaMessageBuilder: KafkaMessageBuilder,
    private val kafkaNewWishItemProducer: IKafkaProducer<NewWishItemMessage>,
    private val kafkaSuggestWishitemsProducer: IKafkaProducer<SuggestMatchingWishItemsMessage>,
) {

    fun create(request: CreateItemRequest): Item {
        val wishlist = wishlistRepository.getById(request.wishlistId)

        val user = User.current()

        if (user.id != wishlist.owner.id) {
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

        val newWishItemMessage = kafkaMessageBuilder.buildNewWishItemMessage(item)
        kafkaNewWishItemProducer.send(newWishItemMessage)

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
        return User.currentOrNull()?.let { user ->
            itemRepository.getUserRecommendations(user.id)
                .takeIf { it.isNotEmpty() }
                ?.filter { wishlistRepository.getById(it.wishlistId).owner.id != user.id }
        }
            ?: itemRepository.getRandomRecommendations()
    }

    fun buildRecommendations(isForce: Boolean = false) {
        val user =
            User.currentOrNull()
                ?: let {
                    println("Current user is null")
                    return
                }

        val items = getAllByUserId(user.id)

        val kafkaMessage =
            kafkaMessageBuilder.buildSuggestMatchingWishItemsMessage(
                user,
                items,
            )

        kafkaSuggestWishitemsProducer.send(kafkaMessage)
    }

    fun getAllByUserId(userId: UUID): List<Item> {
        return itemRepository.getAllByUserId(userId)
    }
}
