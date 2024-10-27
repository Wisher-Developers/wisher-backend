package ru.itmo.wisher.api.example.presentation

import ru.itmo.wisher.api.example.application.domain.PingPong
import ru.itmo.wisher.api.example.presentation.model.PingPong as PingPongDto

fun decode(dto: PingPongDto) =
    PingPong(
        value = dto.value,
    )

fun encode(domain: PingPong) =
    PingPongDto(
        id = domain.id,
        value = domain.value,
    )
