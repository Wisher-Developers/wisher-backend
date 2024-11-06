package ru.itmo.wisher.api.auth.infrastructure

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.User
import ru.itmo.wisher.api.auth.infrastructure.entity.UserJpaRepository
import ru.itmo.wisher.api.auth.application.UserRepository as IUserRepository

@Component
class UserRepository(
    private val userCodec: UserCodec,
    private val userJpaRepository: UserJpaRepository,
) : IUserRepository {
    override suspend fun save(user: User): User {
        val entity = userCodec.encode(user)

        return withContext(Dispatchers.IO) {
            userJpaRepository
                .save(entity)
                .let { userCodec.decode(it) }
        }
    }

    override suspend fun findByUsername(username: String): User? {
        return withContext(Dispatchers.IO) {
            userJpaRepository
                .findByUsername(username)
                ?.let { userCodec.decode(it) }
        }
    }
}
