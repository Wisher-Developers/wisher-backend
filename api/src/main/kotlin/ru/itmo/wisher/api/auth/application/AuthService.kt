package ru.itmo.wisher.api.auth.application

import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.JwtToken
import ru.itmo.wisher.api.auth.domain.LogInRequest
import ru.itmo.wisher.api.auth.domain.SignUpRequest
import ru.itmo.wisher.api.user.application.UserService

@Component
class AuthService(
    private val jwtService: JwtService,
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
) {

    suspend fun signUp(request: SignUpRequest): JwtToken {
        userService.create(request)

        return logIn(
            LogInRequest(
                username = request.username,
                password = request.password,
            ),
        )
    }

    suspend fun logIn(request: LogInRequest): JwtToken {
        val authentication =
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    request.username,
                    request.password,
                ),
            )

        return jwtService.generateToken(authentication)
    }
}
