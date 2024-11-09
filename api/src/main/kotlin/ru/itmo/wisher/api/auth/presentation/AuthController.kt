package ru.itmo.wisher.api.auth.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.wisher.api.auth.application.AuthService

@RestController
@RequestMapping("api/auth")
class AuthController(
    private val authService: AuthService,
) {

    @PostMapping("signup")
    suspend fun signUp(
        @RequestBody request: SignUpRequestDto,
    ): ResponseEntity<AuthResponse> {
        val token = authService.signUp(request.toDomain())

        val response = AuthResponse(token)

        return ResponseEntity.ok(response)
    }

    @PostMapping("login")
    suspend fun logIn(
        @RequestBody request: LogInRequestDto,
    ): ResponseEntity<AuthResponse> {
        val token = authService.logIn(request.toDomain())

        val response = AuthResponse(token)

        return ResponseEntity.ok(response)
    }
}
