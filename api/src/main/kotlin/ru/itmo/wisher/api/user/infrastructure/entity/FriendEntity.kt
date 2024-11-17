package ru.itmo.wisher.api.user.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.MapsId
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.UUID

@Component
interface FriendJpaRepository : CrudRepository<FriendEntity, UUID> {
    @Query("select f from FriendEntity f where f.initiator.id=?1")
    fun findAllByInitiatorId(id: UUID): List<FriendEntity>

    @Query("select f from FriendEntity f where f.initiated.id=?1")
    fun findAllByInitiatedId(id: UUID): List<FriendEntity>
}

@Embeddable
class FriendId(
    @Column(name = "user_id_first")
    var initiatorId: UUID? = null,

    @Column(name = "user_id_second")
    var initiatedId: UUID? = null,
) : Serializable

@Entity
@Table(name = "friend")
class FriendEntity(

    @EmbeddedId
    var id: FriendId,

    @MapsId("initiatorId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_first", referencedColumnName = "id")
    var initiator: UserEntity,

    @MapsId("initiatedId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id_second", referencedColumnName = "id")
    var initiated: UserEntity,
)
