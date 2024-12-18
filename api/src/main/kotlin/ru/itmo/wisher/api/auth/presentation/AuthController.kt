package ru.itmo.wisher.api.auth.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.wisher.api.auth.application.AuthService
import ru.itmo.wisher.api.auth.presentation.model.AuthResponse
import ru.itmo.wisher.api.auth.presentation.model.LogInRequestDto
import ru.itmo.wisher.api.auth.presentation.model.SignUpRequestDto
import ru.itmo.wisher.api.wishes.application.ItemService

@RestController
@RequestMapping("api/auth")
class AuthController(
    private val authService: AuthService,
    private val itemService: ItemService,
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
