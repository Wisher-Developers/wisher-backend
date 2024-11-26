package ru.itmo.wisher.api.user.application

import ru.itmo.wisher.api.user.domain.Request
import java.util.UUID

interface RequestRepository {

    fun save(request: Request): Request

    fun delete(request: Request)

    fun findAllByReceiverId(id: UUID): List<Request>

    fun findAllBySenderId(id: UUID): List<Request>
}
