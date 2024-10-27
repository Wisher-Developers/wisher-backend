package ru.itmo.wisher.api.example.application

import ru.itmo.wisher.api.example.application.domain.PingPong
import java.util.UUID

interface PingPongRepository {
    suspend fun save(pingPong: PingPong): PingPong

    suspend fun get(id: UUID): PingPong
}
