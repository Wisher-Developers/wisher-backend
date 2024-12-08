package ru.itmo.wisher.api.kafka.configuration

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.JsonDeserializer
import ru.itmo.wisher.api.kafka.infrastructure.SuggestMatchingWishItemResponse

@EnableKafka
@Configuration
class KafkaConsumerConfiguration {

    @Value(value = "\${spring.kafka.consumer.group-id}")
    lateinit var groupId: String

    @Bean
    fun consumerFactory(
        @Value(value = "\${spring.kafka.bootstrap-servers}")
        bootstrapAddress: String,
    ): ConsumerFactory<String, SuggestMatchingWishItemResponse> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[ConsumerConfig.GROUP_ID_CONFIG] = groupId
        props[JsonDeserializer.TRUSTED_PACKAGES] = "*"
        return DefaultKafkaConsumerFactory(
            props,
            StringDeserializer(),
            JsonDeserializer(SuggestMatchingWishItemResponse::class.java),
        )
    }

    @Bean
    fun kafkaListenerContainerFactory(
        consumerFactory: ConsumerFactory<String, SuggestMatchingWishItemResponse>,
    ): ConcurrentKafkaListenerContainerFactory<String, SuggestMatchingWishItemResponse> {
        val factory = ConcurrentKafkaListenerContainerFactory<String, SuggestMatchingWishItemResponse>()
        factory.consumerFactory = consumerFactory
        return factory
    }
}
