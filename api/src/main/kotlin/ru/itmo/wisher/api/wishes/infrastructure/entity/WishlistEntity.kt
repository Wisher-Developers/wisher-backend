package ru.itmo.wisher.api.wishes.infrastructure.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.User
import ru.itmo.wisher.api.wishes.domain.Item
import ru.itmo.wisher.api.wishes.domain.PrivateMode
import java.util.UUID

@Component
interface WishlistJpaRepository : CrudRepository<WishlistEntity, UUID> {
    fun findByOwnerId(ownerId: UUID): List<WishlistEntity>
}

@Table(name = "wishlist")
@Entity
class WishlistEntity(
    @Id
    @Column(name = "id")
    var id: UUID,
    @Column(name = "name")
    var name: String,
    @Column(name = "description")
    var description: String? = null,
    @Column(name = "access_link")
    var accessLink: String? = null,
    @Column(name = "private_mode")
    var privateMode: PrivateMode,
    @Column(name = "position")
    var position: Int,
    @ManyToOne
    @Column(name = "owner_id")
    var owner: User,
    @OneToMany(cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, mappedBy = "wishlist")
    var items: MutableList<Item>,
)
