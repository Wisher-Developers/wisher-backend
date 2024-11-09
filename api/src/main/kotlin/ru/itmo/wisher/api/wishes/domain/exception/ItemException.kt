package ru.itmo.wisher.api.wishes.domain.exception

import java.util.UUID

data class ItemException(
    val id: UUID,
) : RuntimeException("Item with id $id not found")
