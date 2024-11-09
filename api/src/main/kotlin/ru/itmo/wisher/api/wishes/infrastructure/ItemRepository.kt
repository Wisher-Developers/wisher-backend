package ru.itmo.wisher.api.wishes.infrastructure

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component
import ru.itmo.wisher.api.wishes.domain.Item
import ru.itmo.wisher.api.wishes.infrastructure.entity.ItemJpaRepository
import java.util.UUID
import ru.itmo.wisher.api.wishes.application.ItemRepository as IItemRepository

@Component
class ItemRepository(
    private val itemCodec: ItemCodec,
    private val itemJpaRepository: ItemJpaRepository,
) : IItemRepository {
    override suspend fun save(item: Item): Item {
        return withContext(Dispatchers.IO) {
            itemCodec.encode(item)
                .let { itemJpaRepository.save(it) }
                .let { itemCodec.decode(it) }
        }
    }

    override suspend fun findById(id: UUID): Item? {
        return withContext(Dispatchers.IO) {
            itemJpaRepository
                .findByIdOrNull(id)
                ?.let { itemCodec.decode(it) }
        }
    }
}
