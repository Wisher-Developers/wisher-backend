package ru.itmo.wisher.api.example.presentation

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.itmo.wisher.api.example.application.PingPongService
import ru.itmo.wisher.api.example.presentation.model.CreatePingPongRequest
import ru.itmo.wisher.api.example.presentation.model.PingPong
import java.util.UUID

@RestController
@RequestMapping("api/ping")
class PingController(
    private val pingPongService: PingPongService,
) {
    @GetMapping("{id}")
    fun get(
        @PathVariable id: UUID,
    ): ResponseEntity<PingPong> {
        val pingPong =
            pingPongService
                .get(id)
                .let { encode(it) }

        return ResponseEntity.ok(pingPong)
    }

    @PostMapping
    fun create(
        @RequestBody request: CreatePingPongRequest,
    ): ResponseEntity<PingPong> {
        val pingPong =
            pingPongService
                .create(request.value)
                .let { encode(it) }

        return ResponseEntity.status(HttpStatus.CREATED).body(pingPong)
    }
}
