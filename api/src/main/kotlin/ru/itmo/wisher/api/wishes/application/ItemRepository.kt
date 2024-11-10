package ru.itmo.wisher.api.wishes.application

import ru.itmo.wisher.api.wishes.domain.Item
import ru.itmo.wisher.api.wishes.domain.exception.ItemException
import java.util.UUID

interface ItemRepository {

    fun save(item: Item): Item

    fun deleteById(id: UUID)

    fun findById(id: UUID): Item?

    fun findByWishlistId(id: UUID): List<Item>

    fun getById(id: UUID): Item {
        return findById(id)
            ?: throw ItemException(id)
    }
}
