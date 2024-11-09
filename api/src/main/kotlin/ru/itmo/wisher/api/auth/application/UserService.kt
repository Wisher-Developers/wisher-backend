package ru.itmo.wisher.api.auth.application

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.SignUpRequest
import ru.itmo.wisher.api.auth.domain.User
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
                password = passwordEncoder.encode(request.password),
                email = request.email,
            )

        userRepository.save(user)
    }

    suspend fun getByUsername(username: String): User {
        return userRepository.getByUsername(username)
    }
}
