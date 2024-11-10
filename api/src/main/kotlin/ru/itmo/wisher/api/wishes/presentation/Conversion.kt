package ru.itmo.wisher.api.wishes.presentation

import ru.itmo.wisher.api.user.presentation.conversion.toDto
import ru.itmo.wisher.api.wishes.domain.CopyItemRequest
import ru.itmo.wisher.api.wishes.domain.CreateItemRequest
import ru.itmo.wisher.api.wishes.domain.CreateWishlistRequest
import ru.itmo.wisher.api.wishes.domain.Item
import ru.itmo.wisher.api.wishes.domain.UpdateWishlistRequest
import ru.itmo.wisher.api.wishes.domain.Wishlist
import ru.itmo.wisher.api.wishes.presentation.model.CopyItemRequestDto
import ru.itmo.wisher.api.wishes.presentation.model.CreateItemRequestDto
import ru.itmo.wisher.api.wishes.presentation.model.CreateWishlistRequestDto
import ru.itmo.wisher.api.wishes.presentation.model.ItemResponse
import ru.itmo.wisher.api.wishes.presentation.model.UpdateWishlistRequestDto
import ru.itmo.wisher.api.wishes.presentation.model.WishlistResponse

fun CreateItemRequestDto.toDomain() =
    CreateItemRequest(
        name = name,
        link = link,
        priority = priority,
        price = price,
        picture = picture,
        description = description,
        wishlistId = wishlistId,
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
        privateMode = privateMode,
    )

fun UpdateWishlistRequestDto.toDomain() =
    UpdateWishlistRequest(
        id = id,
        name = name,
        description = description,
        privateMode = privateMode,
    )

fun Wishlist.toResponse() =
    WishlistResponse(
        id = id,
        name = name,
        description = description,
        accessLink = accessLink,
        privateMode = privateMode,
        position = position,
        owner = owner.toDto(),
        items = items.map { it.toResponse() },
    )
