package ru.itmo.wisher.api.user.infrastructure.entity

import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.IdClass
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface FriendJpaRepository : CrudRepository<FriendEntity, UUID> {
    @Query("select f from FriendEntity f where f.initiator.id=?1")
    fun findAllByInitiatorId(id: UUID): List<FriendEntity>

    @Query("select f from FriendEntity f where f.initiated.id=?1")
    fun findAllByInitiatedId(id: UUID): List<FriendEntity>
}

@Table(name = "friend")
@Entity
@Embeddable
@IdClass(FriendEntity::class)
class FriendEntity(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_first")
    var initiator: UserEntity,
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_second")
    var initiated: UserEntity,
)
