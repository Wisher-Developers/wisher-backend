package ru.itmo.wisher.api.wishes.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface ItemJpaRepository : CrudRepository<ItemEntity, UUID> {
    fun findByWishlistId(wishlistId: String): List<ItemEntity>
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
    var wishlistId: UUID,
    @Column(name = "position")
    var position: Int,
    @Column(name = "idempotency_id")
    var idempotencyId: UUID,
)
