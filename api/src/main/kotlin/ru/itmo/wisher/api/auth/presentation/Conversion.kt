package ru.itmo.wisher.api.auth.presentation

import ru.itmo.wisher.api.auth.domain.LogInRequest
import ru.itmo.wisher.api.auth.domain.SignUpRequest

fun SignUpRequestDto.toDomain() =
    SignUpRequest(
        username = username,
        password = password,
        email = email,
    )

fun LogInRequestDto.toDomain() =
    LogInRequest(
        username = username,
        password = password,
    )
