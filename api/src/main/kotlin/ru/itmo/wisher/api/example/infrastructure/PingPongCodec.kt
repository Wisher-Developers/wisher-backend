package ru.itmo.wisher.api.example.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.example.domain.PingPong
import ru.itmo.wisher.api.example.infrastructure.entity.PingPongEntity

@Component
class PingPongCodec {
    fun encode(domain: PingPong) =
        PingPongEntity(
            id = domain.id,
            value = domain.value,
        )

    fun decode(entity: PingPongEntity) =
        PingPong(
            id = entity.id,
            value = entity.value,
        )
}
