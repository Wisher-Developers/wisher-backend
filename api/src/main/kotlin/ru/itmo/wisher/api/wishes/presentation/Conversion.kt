package ru.itmo.wisher.api.wishes.presentation

import ru.itmo.wisher.api.wishes.domain.CopyItemRequest
import ru.itmo.wisher.api.wishes.domain.CreateItemRequest
import ru.itmo.wisher.api.wishes.domain.CreateWishlistRequest
import ru.itmo.wisher.api.wishes.domain.Item
import ru.itmo.wisher.api.wishes.domain.Wishlist

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

fun CreateWishlistRequestDto.toDomain() =
    CreateWishlistRequest(
        name = name,
        description = description,
        accessLink = accessLink,
        privateMode = privateMode,
        ownerId = ownerId,
    )

fun Wishlist.toResponse() =
    WishlistResponse(
        id = id,
        name = name,
        description = description,
        accessLink = accessLink,
        privateMode = privateMode,
        position = position,
        ownerId = owner.id,
    )
