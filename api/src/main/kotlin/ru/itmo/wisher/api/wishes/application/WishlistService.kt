package ru.itmo.wisher.api.wishes.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.application.FriendRepository
import ru.itmo.wisher.api.user.application.UserRepository
import ru.itmo.wisher.api.user.domain.User
import ru.itmo.wisher.api.wishes.domain.CreateWishlistRequest
import ru.itmo.wisher.api.wishes.domain.PrivateMode
import ru.itmo.wisher.api.wishes.domain.UpdateWishlistRequest
import ru.itmo.wisher.api.wishes.domain.Wishlist
import ru.itmo.wisher.api.wishes.domain.WishlistAccess
import java.util.UUID

@Component
class WishlistService(
    private val wishlistRepository: WishlistRepository,
    private val itemRepository: WishlistRepository,
    private val friendRepository: FriendRepository,
    private val accessRepository: AccessRepository,
    private val userRepository: UserRepository,
) {

    fun create(request: CreateWishlistRequest): Wishlist {
        val user = User.current()
        val wishlist =
            Wishlist(
                id = UUID.randomUUID(),
                name = request.name,
                description = request.description,
                owner = user,
                privateMode = request.privateMode,
                position = getByOwnerId(user.id).size + 1,
                items = mutableListOf(),
            )

        return wishlistRepository.save(wishlist)
    }

    fun getByOwnerId(id: UUID): List<Wishlist> {
        return wishlistRepository.findByOwnerId(id).filter { canGet(it) }
    }

    fun getById(id: UUID): Wishlist {
        if (canGet(wishlistRepository.getById(id)))
            return wishlistRepository.getById(id)

        throw IllegalAccessException("Access denied")
    }

    fun getByLink(link: String): Wishlist {
        return wishlistRepository.getByAccessLink(link)
    }

    fun delete(id: UUID) {
        getById(id).items.forEach { itemRepository.deleteById(it.id) }

        val wishlist = wishlistRepository.getById(id)
        val wishlists = wishlistRepository.findByOwnerId(wishlist.owner.id)
        wishlists.filter { it.position > wishlist.position }
            .map {
                it.position -= 1
                wishlistRepository.save(it)
            }
        wishlistRepository.deleteById(id)
    }

    fun update(request: UpdateWishlistRequest): Wishlist {
        val wishlist = getById(request.id)
        if (request.name != null) {
            wishlist.name = request.name
        }

        if (request.description != null) {
            wishlist.description = request.description
        }

        if (request.privateMode != null) {
            wishlist.privateMode = request.privateMode
        }

        return wishlistRepository.save(wishlist)
    }

    fun generateLink(id: UUID): String {
        var newLink = randomStringByKotlinCollectionRandom()
        while (wishlistRepository.findByAccessLink(newLink) != null) {
            newLink = randomStringByKotlinCollectionRandom()
        }

        val wishlist = wishlistRepository.getById(id)
        wishlist.accessLink = newLink
        wishlistRepository.save(wishlist)
        return newLink
    }

    fun getAllAccessed(id: UUID): List<User> {
        return accessRepository.findByWishlistId(id).map { it.user }
    }

    fun addAccess(wishlistId: UUID, userId: UUID) {
        val user = User.current()
        val wishlist = wishlistRepository.getById(wishlistId)
        if (user.id != wishlist.owner.id)
            throw IllegalAccessException("Access denied")
        val newAccessedUser = userRepository.getById(userId)

        accessRepository.save(WishlistAccess(wishlist, newAccessedUser))
    }

    fun removeAccess(wishlistId: UUID, userId: UUID) {
        val user = User.current()
        val wishlist = wishlistRepository.getById(wishlistId)
        if (user.id != wishlist.owner.id)
            throw IllegalAccessException("Access denied")

        accessRepository.delete(
            accessRepository.findByWishlistId(wishlistId).first { it.user.id == userId },
        )
    }

    private fun canGet(wishlist: Wishlist): Boolean {
        return when (wishlist.privateMode) {
            PrivateMode.PUBLIC -> {
                true
            }
            PrivateMode.FRIENDS -> {
                val user = User.current()
                if (wishlist.owner.id == user.id)
                    return true

                friendRepository.findAllByInitiatedId(user.id).any { it.initiator.id == wishlist.owner.id } ||
                    friendRepository.findAllByInitiatorId(user.id).any { it.initiated.id == wishlist.owner.id }
            }
            PrivateMode.RESTRICTED -> {
                val user = User.current()
                if (wishlist.owner.id == user.id)
                    return true

                false
            }
        }
    }

    private fun randomStringByKotlinCollectionRandom() =
        List(STRING_LENGTH) { charPool.random() }.joinToString("")

    companion object {
        const val STRING_LENGTH = 28
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    }
}
