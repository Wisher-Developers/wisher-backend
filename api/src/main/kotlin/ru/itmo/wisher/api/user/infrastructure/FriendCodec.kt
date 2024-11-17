package ru.itmo.wisher.api.user.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.domain.Friend
import ru.itmo.wisher.api.user.infrastructure.entity.FriendEntity
import ru.itmo.wisher.api.user.infrastructure.entity.FriendId

@Component
class FriendCodec(
    private val userCodec: UserCodec,
) {
    fun encode(domain: Friend) =
        FriendEntity(
            id =
                FriendId(
                    initiatorId = domain.initiator.id,
                    initiatedId = domain.initiated.id,
                ),
            initiator = userCodec.encode(domain.initiator),
            initiated = userCodec.encode(domain.initiated),
        )

    fun decode(entity: FriendEntity) =
        Friend(
            initiator = userCodec.decode(entity.initiator),
            initiated = userCodec.decode(entity.initiated),
        )
}
