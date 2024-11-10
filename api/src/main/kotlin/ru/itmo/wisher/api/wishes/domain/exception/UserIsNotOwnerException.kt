package ru.itmo.wisher.api.wishes.domain.exception

import java.util.UUID

data class UserIsNotOwnerException(
    val id: UUID,
) : RuntimeException("User is not owner of wishlist with id: $id")
