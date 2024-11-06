package ru.itmo.wisher.api.auth.application

import ru.itmo.wisher.api.auth.domain.User
import ru.itmo.wisher.api.auth.domain.exception.UserException

interface UserRepository {

    suspend fun save(user: User): User

    suspend fun findByUsername(username: String): User?

    suspend fun getByUsername(username: String): User {
        return findByUsername(username)
            ?: throw UserException(username)
    }
}
