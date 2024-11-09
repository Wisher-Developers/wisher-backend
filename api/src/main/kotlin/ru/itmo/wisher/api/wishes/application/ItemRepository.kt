package ru.itmo.wisher.api.wishes.application

import ru.itmo.wisher.api.wishes.domain.Item
import ru.itmo.wisher.api.wishes.domain.exception.ItemException
import java.util.UUID

interface ItemRepository {

    suspend fun save(item: Item): Item

    suspend fun findById(id: UUID): Item?

    suspend fun getById(id: UUID): Item {
        return findById(id)
            ?: throw ItemException(id)
    }
}
