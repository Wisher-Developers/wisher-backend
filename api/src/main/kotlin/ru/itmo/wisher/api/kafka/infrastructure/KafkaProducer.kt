package ru.itmo.wisher.api.kafka.infrastructure

import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.kafka.application.IKafkaProducer

@Component
class KafkaNewWishItemMessageProducer(
    private val kafkaTemplate: KafkaTemplate<String, NewWishItemMessage>,
) : IKafkaProducer<NewWishItemMessage> {

    @Value("\${spring.kafka.topics.product-add}")
    private lateinit var topicName: String

    override fun send(message: NewWishItemMessage) {
        kafkaTemplate.send(topicName, message)
        println("Отправлено сообщение $message")
    }
}

@Component
class KafkaSuggestMatchingWishItemsMessageProducer(
    private val kafkaTemplate: KafkaTemplate<String, SuggestMatchingWishItemsMessage>,
) : IKafkaProducer<SuggestMatchingWishItemsMessage> {

    @Value("\${spring.kafka.topics.product-search}")
    private lateinit var topicName: String

    override fun send(message: SuggestMatchingWishItemsMessage) {
        kafkaTemplate.send(topicName, message)
        println("Отправлено сообщение $message")
    }
}
