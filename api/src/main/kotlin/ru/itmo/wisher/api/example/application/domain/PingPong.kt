package ru.itmo.wisher.api.example.application.domain

import java.util.UUID

/**
 * Пингпонг для примера
 *
 * @property id ID пингпонга
 * @property value значение пингпонга
 */
data class PingPong(
    val id: UUID = UUID.randomUUID(),
    val value: String
) {

    /**
     * Супер классно изменить значение пингпонга
     *
     * Добавляет суффикс к пингпонгу
     *
     * @param suffix суффикс для добавления
     *
     * @return копия пингпонга с измененным значением
     */
    fun ultraChange(suffix: String) = copy(value = "$value $suffix")
}