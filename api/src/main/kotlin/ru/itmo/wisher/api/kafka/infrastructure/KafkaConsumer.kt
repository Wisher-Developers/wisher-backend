package ru.itmo.wisher.api.kafka.infrastructure

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.Item

@Component
class KafkaConsumer {

    @KafkaListener(topics = ["topicName"], groupId = "foo")
    fun listenGroupFoo(message: Item) {
        println("Received Message in group foo: $message")
    }
}
