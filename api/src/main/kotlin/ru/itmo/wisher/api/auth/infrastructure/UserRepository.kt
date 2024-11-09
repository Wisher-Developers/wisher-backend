package ru.itmo.wisher.api.auth.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.User
import ru.itmo.wisher.api.auth.infrastructure.entity.UserJpaRepository
import ru.itmo.wisher.api.auth.application.UserRepository as IUserRepository

@Component
class UserRepository(
    private val userCodec: UserCodec,
    private val userJpaRepository: UserJpaRepository,
) : IUserRepository {

    override fun save(user: User): User {
        return userCodec
            .encode(user)
            .let { userJpaRepository.save(it) }
            .let { userCodec.decode(it) }
    }

    override fun findByUsername(username: String): User? {
        return userJpaRepository
            .findByUsername(username)
            ?.let { userCodec.decode(it) }
    }
}
