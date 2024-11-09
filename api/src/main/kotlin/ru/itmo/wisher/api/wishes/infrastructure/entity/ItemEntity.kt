package ru.itmo.wisher.api.wishes.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.Wishlist
import java.util.UUID

@Component
interface ItemJpaRepository : CrudRepository<ItemEntity, UUID> {
    fun findByWishlistId(wishlistId: UUID): List<ItemEntity>
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
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "wishlist_id")
    val wishlist: Wishlist,
    @Column(name = "position")
    var position: Int,
    @Column(name = "idempotency_id")
    var idempotencyId: UUID,
)
