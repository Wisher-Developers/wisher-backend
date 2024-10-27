package ru.itmo.wisher.api.example.infrastructure.entity

import jakarta.persistence.*
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.UUID

@Component
interface PingPongJpaRepository : CrudRepository<PingPongEntity, UUID>

@Table(name = "ping_pong")
@Entity
class PingPongEntity(
    @Id
    var id: UUID,
    @Column(name = "value")
    var value: String,
)
