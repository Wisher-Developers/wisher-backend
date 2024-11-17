package ru.itmo.wisher.api.user.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.itmo.wisher.api.user.application.FriendProcessingService
import ru.itmo.wisher.api.user.presentation.conversion.toDto
import ru.itmo.wisher.api.user.presentation.model.User
import java.util.UUID

@RestController
@RequestMapping("api/friends")
class FriendController(
    private val friendProcessingService: FriendProcessingService,
) {
    @GetMapping("/")
    fun getFriends(): ResponseEntity<List<User>> {
        val friends = friendProcessingService.getFriends()
        return ResponseEntity.ok(friends.map { it.toDto() })
    }

    @GetMapping("/request/in/")
    fun getRequestsReceived(): ResponseEntity<List<User>> {
        val requests = friendProcessingService.getRequestsReceived()
        return ResponseEntity.ok(requests.map { it.toDto() })
    }

    @GetMapping("/request/out/")
    fun getRequestsSent(): ResponseEntity<List<User>> {
        val requests = friendProcessingService.getRequestsSent()
        return ResponseEntity.ok(requests.map { it.toDto() })
    }

    @PostMapping("/request/send/{id}")
    fun sendRequest(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        friendProcessingService.sendRequest(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/request/accept/{id}")
    fun acceptRequest(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        friendProcessingService.acceptRequest(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/request/deny/{id}")
    fun denyRequest(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        friendProcessingService.denyRequest(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/request/cancel/{id}")
    fun cancelRequest(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        friendProcessingService.cancelRequest(id)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/remove/{id}")
    fun removeFriend(
        @PathVariable id: UUID,
    ): ResponseEntity<Void> {
        friendProcessingService.removeFriend(id)
        return ResponseEntity.noContent().build()
    }
}
