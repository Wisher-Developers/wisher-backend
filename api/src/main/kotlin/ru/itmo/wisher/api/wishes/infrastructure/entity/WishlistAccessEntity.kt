package ru.itmo.wisher.api.wishes.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.infrastructure.entity.UserEntity
import java.io.Serializable
import java.util.UUID

@Component
interface WishlistAccessJpaRepository : CrudRepository<WishlistAccessEntity, UUID> {
    fun findByWishlistId(wishlistId: UUID): List<WishlistAccessEntity>
}

@Embeddable
class WishlistAccessId(
    @Column(name = "wishlist_id")
    var wishlistId: UUID? = null,

    @Column(name = "user_id")
    var userId: UUID? = null,
) : Serializable

@Table(name = "wishlist_access")
@Entity
class WishlistAccessEntity(
    @EmbeddedId
    var id: WishlistAccessId,

    @MapsId("wishlistId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", referencedColumnName = "id")
    var wishlist: WishlistEntity,

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: UserEntity,
)
