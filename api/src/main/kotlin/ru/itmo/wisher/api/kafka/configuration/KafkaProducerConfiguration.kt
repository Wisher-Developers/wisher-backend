package ru.itmo.wisher.api.kafka.configuration

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer
import ru.itmo.wisher.api.kafka.infrastructure.NewWishItemMessage
import ru.itmo.wisher.api.kafka.infrastructure.SuggestMatchingWishItemsMessage

@Configuration
class KafkaProducerConfiguration {

    @Bean
    fun newWishItemMessageProducerFactory(
        @Value(value = "\${spring.kafka.bootstrap-servers}")
        bootstrapAddress: String,
    ): ProducerFactory<String, NewWishItemMessage> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun suggestMatchingWishItemsMessageProducerFactory(
        @Value(value = "\${spring.kafka.bootstrap-servers}")
        bootstrapAddress: String,
    ): ProducerFactory<String, SuggestMatchingWishItemsMessage> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = JsonSerializer::class.java
        return DefaultKafkaProducerFactory(configProps)
    }

    @Bean
    fun newWishItemMessageKafkaTemplate(
        @Qualifier(
            "newWishItemMessageProducerFactory",
        ) producerFactory: ProducerFactory<String, NewWishItemMessage>,
    ): KafkaTemplate<String, NewWishItemMessage> {
        return KafkaTemplate(producerFactory)
    }

    @Bean
    fun suggestMatchingWishItemsMessageKafkaTemplate(
        @Qualifier(
            "suggestMatchingWishItemsMessageProducerFactory",
        ) producerFactory: ProducerFactory<String, SuggestMatchingWishItemsMessage>,
    ): KafkaTemplate<String, SuggestMatchingWishItemsMessage> {
        return KafkaTemplate(producerFactory)
    }
}
