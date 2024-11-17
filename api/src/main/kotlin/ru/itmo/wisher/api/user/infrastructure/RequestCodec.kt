package ru.itmo.wisher.api.user.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.domain.Request
import ru.itmo.wisher.api.user.infrastructure.entity.RequestEntity
import ru.itmo.wisher.api.user.infrastructure.entity.RequestId

@Component
class RequestCodec(
    private val userCodec: UserCodec,
) {
    fun encode(domain: Request) =
        RequestEntity(
            id =
                RequestId(
                    senderId = domain.sender.id,
                    receiverId = domain.receiver.id,
                ),
            sender = userCodec.encode(domain.sender),
            receiver = userCodec.encode(domain.receiver),
        )

    fun decode(entity: RequestEntity) =
        Request(
            sender = userCodec.decode(entity.sender),
            receiver = userCodec.decode(entity.receiver),
        )
}
