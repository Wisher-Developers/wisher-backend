package ru.itmo.wisher.api.auth.application

import ru.itmo.wisher.api.auth.domain.User
import ru.itmo.wisher.api.auth.domain.exception.UserException

interface UserRepository {

    fun save(user: User): User

    fun findByUsername(username: String): User?

    fun getByUsername(username: String): User {
        return findByUsername(username)
            ?: throw UserException(username)
    }
}
