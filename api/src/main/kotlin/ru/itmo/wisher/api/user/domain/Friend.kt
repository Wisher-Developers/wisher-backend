package ru.itmo.wisher.api.user.domain

data class Friend(
    val initiator: User,
    val initiated: User,
)
