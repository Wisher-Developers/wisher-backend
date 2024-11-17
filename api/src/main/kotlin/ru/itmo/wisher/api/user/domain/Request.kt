package ru.itmo.wisher.api.user.domain

data class Request(
    val sender: User,
    val receiver: User,
)
