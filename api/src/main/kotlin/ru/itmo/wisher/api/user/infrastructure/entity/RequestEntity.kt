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
import java.io.Serializable
import java.util.UUID

@Component
interface RequestJpaRepository : CrudRepository<RequestEntity, UUID> {
    @Query("select r from RequestEntity r where r.sender.id=?1")
    fun findAllBySenderId(id: UUID): List<RequestEntity>

    @Query("select r from RequestEntity r where r.receiver.id=?1")
    fun findAllByReceiverId(id: UUID): List<RequestEntity>
}

@Table(name = "friend_request")
@Entity
@Embeddable
@IdClass(RequestEntity::class)
data class RequestEntity(
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    var sender: UserEntity,
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    var receiver: UserEntity,
) : Serializable
