package ru.itmo.wisher.api.kafka.application

interface IKafkaProducer<T> {
    fun send(message: T)
}
