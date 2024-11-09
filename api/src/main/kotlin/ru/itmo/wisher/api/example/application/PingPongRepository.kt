package ru.itmo.wisher.api.example.application

import ru.itmo.wisher.api.example.domain.PingPong
import java.util.UUID

interface PingPongRepository {
    fun save(pingPong: PingPong): PingPong

    fun get(id: UUID): PingPong
}
