package ru.itmo.wisher.api.user.application

import ru.itmo.wisher.api.user.domain.Friend
import java.util.UUID

interface FriendRepository {

    fun save(friend: Friend): Friend

    fun delete(friend: Friend)

    fun findAllByInitiatorId(id: UUID): List<Friend>

    fun findAllByInitiatedId(id: UUID): List<Friend>
}
