package ru.itmo.wisher.api.kafka.infrastructure

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.Item

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, Item>,
) {

    fun sendMessage(msg: Item) {
        kafkaTemplate.send("topicName", msg)
    }
}
