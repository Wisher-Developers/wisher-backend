package ru.itmo.wisher.api.example.infrastructure

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.example.domain.PingPong
import ru.itmo.wisher.api.example.infrastructure.entity.PingPongJpaRepository
import java.util.*
import ru.itmo.wisher.api.example.application.PingPongRepository as IPingPongRepository

@Component
class PingPongRepository(
    private val pingPongCodec: PingPongCodec,
    private val jpaRepository: PingPongJpaRepository,
) : IPingPongRepository {
    override suspend fun save(pingPong: PingPong): PingPong {
        return pingPongCodec
            .encode(pingPong)
            .let { jpaRepository.save(it) }
            .let { pingPongCodec.decode(it) }
    }

    override suspend fun get(id: UUID): PingPong {
        return withContext(Dispatchers.IO) {
            jpaRepository
                .findById(id)
                .map { pingPongCodec.decode(it) }
                .orElseThrow { IllegalArgumentException("PingPong with id $id not found") }
        }
    }
}
