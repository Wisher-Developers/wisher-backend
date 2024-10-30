package ru.itmo.wisher.api.example.application

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.example.application.domain.PingPong
import java.util.UUID

/**
 * Сервис-пример для примера.
 *
 * @property pingPongRepository для работы PingPong
 */
@Component
class PingPongService(
    private val pingPongRepository: PingPongRepository,
) {
    suspend fun get(id: UUID): PingPong = pingPongRepository.get(id)

    /**
     * Создать PingPong
     *
     * @param value значение пингпонга
     *
     * @return созданный пингпонг
     */
    suspend fun create(value: String): PingPong {
        val pingPong =
            PingPong(
                id = UUID.randomUUID(),
                value = value,
            )

        return pingPongRepository.save(pingPong)
    }

    fun update(pingPong: PingPong): PingPong = pingPong.ultraChange(suffix = "chunga-changa")
}
