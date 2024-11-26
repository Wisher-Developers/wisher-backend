package ru.itmo.wisher.api.user.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.domain.Friend
import ru.itmo.wisher.api.user.infrastructure.entity.FriendJpaRepository
import java.util.UUID
import ru.itmo.wisher.api.user.application.FriendRepository as IFriendRepository

@Component
class FriendRepository(
    private val friendCodec: FriendCodec,
    private val friendJpaRepository: FriendJpaRepository,
) : IFriendRepository {
    override fun save(friend: Friend): Friend {
        return friendCodec
            .encode(friend)
            .let { friendJpaRepository.save(it) }
            .let { friendCodec.decode(it) }
    }

    override fun delete(friend: Friend) {
        friendJpaRepository.delete(friendCodec.encode(friend))
    }

    override fun findAllByInitiatorId(id: UUID): List<Friend> {
        return friendJpaRepository
            .findAllByInitiatorId(id)
            .map {
                friendCodec.decode(it)
            }
    }

    override fun findAllByInitiatedId(id: UUID): List<Friend> {
        return friendJpaRepository
            .findAllByInitiatedId(id)
            .map {
                friendCodec.decode(it)
            }
    }
}
