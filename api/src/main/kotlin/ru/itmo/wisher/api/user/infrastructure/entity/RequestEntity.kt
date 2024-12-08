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
interface RequestJpaRepository : CrudRepository<RequestEntity, UUID> {
    @Query("select r from RequestEntity r where r.sender.id=?1")
    fun findAllBySenderId(id: UUID): List<RequestEntity>

    @Query("select r from RequestEntity r where r.receiver.id=?1")
    fun findAllByReceiverId(id: UUID): List<RequestEntity>
}

@Embeddable
class RequestId(
    @Column(name = "sender_id")
    var senderId: UUID? = null,

    @Column(name = "receiver_id")
    var receiverId: UUID? = null,
) : Serializable

@Entity
@Table(name = "friend_request")
data class RequestEntity(

    @EmbeddedId
    var id: RequestId,

    @MapsId("senderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", referencedColumnName = "id")
    var sender: UserEntity,

    @MapsId("receiverId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id", referencedColumnName = "id")
    var receiver: UserEntity,
) : Serializable
