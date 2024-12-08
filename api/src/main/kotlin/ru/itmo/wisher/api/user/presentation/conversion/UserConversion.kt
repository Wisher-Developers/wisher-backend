package ru.itmo.wisher.api.user.presentation.conversion

import ru.itmo.wisher.api.user.domain.UpdateUserRequest
import ru.itmo.wisher.api.user.domain.User
import ru.itmo.wisher.api.user.presentation.model.UpdateUserRequestDto
import ru.itmo.wisher.api.user.presentation.model.User as UserDto

fun User.toDto() =
    UserDto(
        id = id,
        username = userName,
        email = email,
        avatar = avatarLink,
    )

fun UpdateUserRequestDto.toDomain() =
    UpdateUserRequest(
        id = id,
        username = username,
        email = email,
        password = password,
        avatar = avatar,
    )
