package ru.itmo.wisher.api.auth.application

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.JwtToken
import ru.itmo.wisher.api.auth.domain.SignUpRequest

@Component
class AuthService(
    private val jwtService: JwtService,
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
) {

    suspend fun signUp(request: SignUpRequest): JwtToken {
        userService.create(request)

        return logIn(
            username = request.username,
            password = request.password,
        )
    }

    suspend fun logIn(username: String, password: String): JwtToken {
        val authToken =
            UsernamePasswordAuthenticationToken(
                username,
                password,
            )

        authenticationManager.authenticate(authToken)

        return userService
            .getByUsername(username)
            .let { jwtService.generateToken(it) }
    }
}
