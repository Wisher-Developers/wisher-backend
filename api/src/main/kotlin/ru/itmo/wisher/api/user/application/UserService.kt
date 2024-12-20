package ru.itmo.wisher.api.user.application

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.SignUpRequest
import ru.itmo.wisher.api.user.domain.UpdateUserRequest
import ru.itmo.wisher.api.user.domain.User
import java.time.Instant
import java.util.*

@Component
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    suspend fun create(request: SignUpRequest) {
        val user =
            User(
                id = UUID.randomUUID(),
                userName = request.username,
                passWord = passwordEncoder.encode(request.password),
                email = request.email,
                lastLogin = Instant.now(),
            )

        userRepository.save(user)
    }

    suspend fun getByUsername(username: String): User {
        return userRepository.getByUsername(username)
    }

    fun getById(id: UUID): User {
        return userRepository.getById(id)
    }

    fun save(user: User): User {
        return userRepository.save(user)
    }

    fun getBySubstring(substring: String): List<User> {
        val user = User.currentOrNull()

        return userRepository
            .findAllByUsernameSubstring(substring)
            .filterNot { it.id == user?.id }
    }

    fun update(request: UpdateUserRequest): User {
        val user = getById(request.id)
        if (request.username != null) {
            user.userName = request.username
        }

        if (request.password != null) {
            user.passWord = request.password
        }

        if (request.email != null) {
            user.email = request.email
        }

        if (request.avatar != null) {
            user.avatarLink = request.avatar
        }

        return userRepository.save(user)
    }
}
