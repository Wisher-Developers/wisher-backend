package ru.itmo.wisher.api.wishes.presentation

import ru.itmo.wisher.api.wishes.domain.CopyItemRequest
import ru.itmo.wisher.api.wishes.domain.CreateItemRequest
import ru.itmo.wisher.api.wishes.domain.Item

fun CreateItemRequestDto.toDomain() =
    CreateItemRequest(
        name = name,
        link = link,
        priority = priority,
        price = price,
        picture = picture,
        description = description,
        wishlistId = wishlistId,
        position = position,
    )

fun CopyItemRequestDto.toDomain() =
    CopyItemRequest(
        oldId = oldId,
        name = name,
        link = link,
        priority = priority,
        price = price,
        picture = picture,
        description = description,
        wishlistId = wishlistId,
        position = position,
    )

fun Item.toResponse() =
    ItemResponse(
        id = id,
        name = name,
        link = link,
        priority = priority,
        price = price,
        picture = picture,
        description = description,
        wishlistId = wishlistId,
        position = position,
    )
