package ru.itmo.wisher.api.auth.application

import ru.itmo.wisher.api.auth.domain.User
import ru.itmo.wisher.api.auth.domain.exception.UserException
import ru.itmo.wisher.api.auth.domain.exception.UserIdException
import java.util.UUID

interface UserRepository {

    fun save(user: User): User

    fun findByUsername(username: String): User?

    fun findById(id: UUID): User?

    fun getById(id: UUID): User {
        return findById(id)
            ?: throw UserIdException(id)
    }

    fun getByUsername(username: String): User {
        return findByUsername(username)
            ?: throw UserException(username)
    }
}
