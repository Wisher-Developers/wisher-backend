package ru.itmo.wisher.api.user.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.domain.Request
import ru.itmo.wisher.api.user.infrastructure.entity.RequestEntity

@Component
class RequestCodec(
    private val userCodec: UserCodec,
) {
    fun encode(domain: Request) =
        RequestEntity(
            sender = userCodec.encode(domain.sender),
            receiver = userCodec.encode(domain.receiver),
        )

    fun decode(entity: RequestEntity) =
        Request(
            sender = userCodec.decode(entity.sender),
            receiver = userCodec.decode(entity.receiver),
        )
}
