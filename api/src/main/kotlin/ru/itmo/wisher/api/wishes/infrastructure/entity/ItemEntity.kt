package ru.itmo.wisher.api.wishes.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.domain.Limit
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.UUID

@Component
interface ItemJpaRepository : CrudRepository<ItemEntity, UUID> {
    fun findByWishlistId(wishlistId: UUID): List<ItemEntity>

    @Query(
        "SELECT * FROM item",
        nativeQuery = true,
    )
    fun getRecommendationsUnauthorized(limit: Limit): List<ItemEntity>

    @Query(
        "SELECT * FROM item WHERE wishlist_id IN (SELECT id FROM wishlist WHERE owner_id=?1)",
        nativeQuery = true,
    )
    fun getAllByUserId(userId: UUID): List<ItemEntity>
}

@Component
interface UserRecommendationJpaRepository : CrudRepository<UserRecommendationEntity, UserRecommendationEntity.Id> {

    @Query(
        "SELECT * FROM item_user WHERE user_id = ?1",
        nativeQuery = true,
    )
    fun getRecommendations(id: UUID, limit: Limit): List<ItemEntity>
}

@Table(name = "item")
@Entity
class ItemEntity(
    @Id
    @Column(name = "id")
    var id: UUID,
    @Column(name = "name")
    var name: String,
    @Column(name = "link")
    var link: String? = null,
    @Column(name = "priority")
    var priority: Int? = null,
    @Column(name = "price")
    var price: Int? = null,
    @Column(name = "picture")
    var picture: String? = null,
    @Column(name = "description")
    var description: String? = null,
    @Column(name = "wishlist_id")
    val wishlistId: UUID,
    @Column(name = "position")
    var position: Int,
    @Column(name = "idempotency_id")
    var idempotencyId: UUID,
)

@Table(name = "item_user")
@Entity
class UserRecommendationEntity(
    @EmbeddedId
    var id: Id,
    @Column(name = "index_number")
    var indexNumber: Int,
) {
    @Embeddable
    class Id(
        @Column(name = "user_id")
        var userId: UUID,
        @Column(name = "item_id")
        var itemId: UUID,
    ) : Serializable
}
