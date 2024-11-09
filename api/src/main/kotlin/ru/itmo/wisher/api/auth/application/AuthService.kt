package ru.itmo.wisher.api.auth.application

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.JwtToken
import ru.itmo.wisher.api.auth.domain.SignUpRequest

@Component
class AuthService(
    private val jwtService: JwtService,
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
) {

    suspend fun signUp(request: SignUpRequest) {
        userService.create(request)
    }

    suspend fun logIn(username: String, password: String): JwtToken {
        val authentication =
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    username,
                    password,
                ),
            )

        SecurityContextHolder.getContext().authentication = authentication
        val token: String = jwtService.generateToken(authentication)

        return token
    }
}
