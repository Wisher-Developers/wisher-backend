package ru.itmo.wisher.api.kafka.infrastructure

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.infrastructure.entity.UserJpaRepository
import ru.itmo.wisher.api.wishes.infrastructure.entity.UserRecommendationEntity
import ru.itmo.wisher.api.wishes.infrastructure.entity.UserRecommendationJpaRepository

@Component
class KafkaConsumer(
    private val recommendationJpaRepository: UserRecommendationJpaRepository,
    private val userJpaRepository: UserJpaRepository,
) {

    @KafkaListener(topics = ["product-response"], groupId = "wisher")
    fun consume(message: SuggestMatchingWishItemResponse) {
        println("Received Message: $message")

        val userId =
            try {
                userJpaRepository.getUserIdByRecommendationRequestId(message.requestId)
            } catch (ex: RuntimeException) {
                println("Request Id из прошлых запусков, поэтому ответ проигнорирован")
                return
            }

        val recommendations =
            message.products.mapIndexed { index, uuid ->
                UserRecommendationEntity(
                    id =
                        UserRecommendationEntity.Id(
                            userId = userId,
                            itemId = uuid,
                        ),
                    indexNumber = index,
                )
            }

        recommendationJpaRepository.saveAll(recommendations)
    }
}
