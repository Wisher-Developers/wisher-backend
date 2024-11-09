package ru.itmo.wisher.api.user.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.domain.User
import ru.itmo.wisher.api.user.infrastructure.entity.UserJpaRepository
import java.util.UUID
import ru.itmo.wisher.api.user.application.UserRepository as IUserRepositoryinfrastructure/UserRepository.kt

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

    override fun getById(id: UUID): User {
        return userJpaRepository
            .findById(id)
            .orElseThrow()
            .let { userCodec.decode(it) }
    }

    override fun findByUsername(username: String): User? {
        return userJpaRepository
            .findByUsername(username)
            ?.let { userCodec.decode(it) }
    }

    override fun findById(id: UUID): User? {
        return userJpaRepository
            .findById(id)
            .get()
            .let { userCodec.decode(it) }
    }
}
