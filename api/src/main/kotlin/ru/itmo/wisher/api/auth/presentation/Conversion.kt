package ru.itmo.wisher.api.auth.presentation

import ru.itmo.wisher.api.auth.domain.LogInRequest
import ru.itmo.wisher.api.auth.domain.SignUpRequest
import ru.itmo.wisher.api.auth.presentation.model.LogInRequestDto
import ru.itmo.wisher.api.auth.presentation.model.SignUpRequestDto

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
