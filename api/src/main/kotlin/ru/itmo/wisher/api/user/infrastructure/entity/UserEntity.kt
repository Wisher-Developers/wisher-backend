package ru.itmo.wisher.api.user.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
interface UserJpaRepository : CrudRepository<UserEntity, UUID> {
    fun findByUsername(username: String): UserEntity?

    @Query("SELECT u.id FROM UserEntity u WHERE u.lastRequestRecommendationId = :id")
    fun getUserIdByRecommendationRequestId(id: UUID): UUID
}

@Table(name = "users")
@Entity
class UserEntity(
    @Id
    @Column(name = "id")
    var id: UUID,
    @Column(name = "login")
    var username: String,
    @Column(name = "password")
    var password: String,
    @Column(name = "email")
    var email: String,
    @Column(name = "avatar")
    var avatar: String? = null,
    @Column(name = "last_login")
    var lastLogin: Instant,
    @Column(name = "last_request_recommendation_id")
    var lastRequestRecommendationId: UUID? = null,
)
