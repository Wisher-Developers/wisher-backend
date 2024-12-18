package ru.itmo.wisher.api.kafka.configuration

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaAdmin

@Configuration
class KafkaTopicConfiguration {

    @Bean
    fun kafkaAdmin(
        @Value(value = "\${spring.kafka.bootstrap-servers}")
        bootstrapAddress: String,
    ): KafkaAdmin {
        val configs: MutableMap<String, Any?> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun productAddTopic(
        @Value("\${spring.kafka.topics.product-add}")
        topicName: String,
    ): NewTopic {
        return NewTopic(topicName, 1, 1.toShort())
    }

    @Bean
    fun productSearchTopic(
        @Value("\${spring.kafka.topics.product-search}")
        topicName: String,
    ): NewTopic {
        return NewTopic(topicName, 1, 1.toShort())
    }

    @Bean
    fun productResponseTopic(
        @Value("\${spring.kafka.topics.product-response}")
        topicName: String,
    ): NewTopic {
        return NewTopic(topicName, 1, 1.toShort())
    }
}
