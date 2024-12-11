package ru.itmo.wisher.api.wishes.infrastructure

import org.springframework.data.domain.Limit
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.Item
import ru.itmo.wisher.api.wishes.infrastructure.entity.ItemJpaRepository
import ru.itmo.wisher.api.wishes.infrastructure.entity.UserRecommendationJpaRepository
import java.util.UUID
import ru.itmo.wisher.api.wishes.application.ItemRepository as IItemRepository

@Component
class ItemRepository(
    private val itemCodec: ItemCodec,
    private val itemJpaRepository: ItemJpaRepository,
    private val userRecommendationJpaRepository: UserRecommendationJpaRepository,
) : IItemRepository {
    override fun save(item: Item): Item {
        return itemCodec.encode(item)
            .let { itemJpaRepository.save(it) }
            .let { itemCodec.decode(it) }
    }

    override fun findById(id: UUID): Item? {
        return itemJpaRepository
            .findByIdOrNull(id)
            ?.let { itemCodec.decode(it) }
    }

    override fun findByWishlistId(id: UUID): List<Item> {
        return itemJpaRepository
            .findByWishlistId(id)
            .map { itemCodec.decode(it) }
    }

    override fun deleteById(id: UUID) {
        itemJpaRepository.deleteById(id)
    }

    override fun getUserRecommendations(id: UUID): List<Item> {
        return userRecommendationJpaRepository
            .getRecommendations(id, Limit.of(RECOMMENDATIONS_LIMIT))
            .sortedBy { it.indexNumber }
            .mapNotNull { findById(it.id.itemId) }
    }

    override fun getRandomRecommendations(): List<Item> {
        return itemJpaRepository
            .getRecommendationsUnauthorized(Limit.of(RECOMMENDATIONS_LIMIT))
            .map { itemCodec.decode(it) }
    }

    override fun getAllByUserId(userId: UUID): List<Item> {
        return itemJpaRepository
            .getAllByUserId(userId)
            .map { itemCodec.decode(it) }
    }

    companion object {
        const val RECOMMENDATIONS_LIMIT: Int = 100
    }
}
