package ru.itmo.wisher.api.user.application

import ru.itmo.wisher.api.user.domain.User
import ru.itmo.wisher.api.user.domain.exception.NoSuchId
import ru.itmo.wisher.api.user.domain.exception.NoSuchUsername
import java.util.UUID

interface UserRepository {

    fun save(user: User): User

    fun findById(id: UUID): User?

    fun findByUsername(username: String): User?

    fun getByUsername(username: String): User {
        return findByUsername(username)
            ?: throw NoSuchUsername(username)
    }

    fun getById(id: UUID): User {
        return findById(id)
            ?: throw NoSuchId(id)
    }
}
