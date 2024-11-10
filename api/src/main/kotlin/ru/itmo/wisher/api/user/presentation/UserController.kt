package ru.itmo.wisher.api.user.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.wisher.api.user.application.UserService
import ru.itmo.wisher.api.user.domain.User
import ru.itmo.wisher.api.user.presentation.conversion.toDto
import java.util.UUID
import ru.itmo.wisher.api.user.presentation.model.User as UserDto

@RestController
@RequestMapping("api/users")
class UserController(
    private val userService: UserService,
) {

    @GetMapping("me")
    fun me(): ResponseEntity<UserDto> {
        val user = User.current()

        return ResponseEntity.ok(user.toDto())
    }

    @GetMapping("{id}")
    fun getById(
        @PathVariable id: UUID,
    ): ResponseEntity<UserDto> {
        val user = userService.getById(id)

        return ResponseEntity.ok(user.toDto())
    }
}
