package ru.itmo.wisher.api.kafka.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.kafka.infrastructure.NewWishItemMessage
import ru.itmo.wisher.api.kafka.infrastructure.SuggestMatchingWishItemsMessage
import ru.itmo.wisher.api.user.application.UserRepository
import ru.itmo.wisher.api.user.domain.User
import ru.itmo.wisher.api.wishes.domain.Item
import java.util.*

@Component
class KafkaMessageBuilder(
    private val userRepository: UserRepository,
) {

    fun buildNewWishItemMessage(item: Item): NewWishItemMessage {
        return NewWishItemMessage(
            uuid = item.id,
            content = item.description ?: item.name,
        )
    }

    fun buildSuggestMatchingWishItemsMessage(user: User, items: List<Item>): SuggestMatchingWishItemsMessage {
        val requestId = UUID.randomUUID()

        val message =
            SuggestMatchingWishItemsMessage(
                requestId = requestId,
                products = items.map { it.id },
            )

        val userWithRequestId = user.withRecommendationId(requestId)

        userRepository.save(userWithRequestId)

        return message
    }
}
