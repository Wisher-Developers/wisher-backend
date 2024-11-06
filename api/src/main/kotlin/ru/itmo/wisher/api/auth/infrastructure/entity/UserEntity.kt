package ru.itmo.wisher.api.auth.infrastructure.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component
import java.util.*

@Component
interface UserJpaRepository : CrudRepository<UserEntity, UUID> {
    fun findByUsername(username: String): UserEntity?
}

@Table(name = "user")
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
)
