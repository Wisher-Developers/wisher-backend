package ru.itmo.wisher.api.user.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.domain.Friend
import ru.itmo.wisher.api.user.domain.Request
import ru.itmo.wisher.api.user.domain.User
import java.util.*

@Component
class FriendProcessingService(
    private val userRepository: UserRepository,
    private val friendRepository: FriendRepository,
    private val requestRepository: RequestRepository,
) {
    fun getFriends(id: UUID): List<User> {
        return friendRepository.findAllByInitiatedId(id).map { it.initiator } +
            friendRepository.findAllByInitiatorId(id).map { it.initiator }
    }

    fun getRequestsReceived(): List<User> {
        val user = User.currentOrNull() ?: throw NullPointerException()
        return requestRepository.findAllByReceiverId(user.id).map { it.sender }
    }

    fun getRequestsSent(): List<User> {
        val user = User.currentOrNull() ?: throw NullPointerException()
        return requestRepository.findAllBySenderId(user.id).map { it.receiver }
    }

    fun sendRequest(id: UUID) {
        val user = User.currentOrNull() ?: throw NullPointerException()
        if (requestRepository.findAllBySenderId(id).any { it.receiver.id == user.id }) {
            acceptRequest(id)
        } else {
            requestRepository.save(
                Request(
                    userRepository.getById(user.id),
                    userRepository.getById(id),
                ),
            )
        }
    }

    fun acceptRequest(id: UUID) {
        val user = User.currentOrNull() ?: throw NullPointerException()
        requestRepository.delete(
            Request(
                userRepository.getById(id),
                userRepository.getById(user.id),
            ),
        )
        friendRepository.save(
            Friend(
                userRepository.getById(id),
                userRepository.getById(user.id),
            ),
        )
    }

    fun denyRequest(id: UUID) {
        val user = User.currentOrNull() ?: throw NullPointerException()
        requestRepository.delete(
            Request(
                userRepository.getById(id),
                userRepository.getById(user.id),
            ),
        )
    }

    fun cancelRequest(id: UUID) {
        val user = User.currentOrNull() ?: throw NullPointerException()
        requestRepository.delete(
            Request(
                userRepository.getById(user.id),
                userRepository.getById(id),
            ),
        )
    }

    fun removeFriend(id: UUID) {
        val user = User.currentOrNull() ?: throw NullPointerException()
        var remove =
            friendRepository.findAllByInitiatedId(
                user.id,
            ).firstOrNull { it.initiator.id == id }

        if (remove == null) {
            remove = friendRepository.findAllByInitiatorId(user.id).firstOrNull { it.initiated.id == id }
        }
        if (remove == null) {
            throw NullPointerException()
        }
        friendRepository.delete(remove)
    }
}
