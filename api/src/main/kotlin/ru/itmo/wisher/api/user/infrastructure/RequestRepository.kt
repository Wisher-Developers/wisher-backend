package ru.itmo.wisher.api.user.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.user.domain.Request
import ru.itmo.wisher.api.user.infrastructure.entity.RequestJpaRepository
import java.util.UUID
import ru.itmo.wisher.api.user.application.RequestRepository as IRequestRepository

@Component
class RequestRepository(
    private val requestCodec: RequestCodec,
    private val requestJpaRepository: RequestJpaRepository,
) : IRequestRepository {
    override fun save(request: Request): Request {
        return requestCodec
            .encode(request)
            .let { requestJpaRepository.save(it) }
            .let { requestCodec.decode(it) }
    }

    override fun delete(request: Request) {
        requestJpaRepository.delete(requestCodec.encode(request))
    }

    override fun findAllByReceiverId(id: UUID): List<Request> {
        return requestJpaRepository
            .findAllByReceiverId(id)
            .map {
                requestCodec.decode(it)
            }
    }

    override fun findAllBySenderId(id: UUID): List<Request> {
        return requestJpaRepository
            .findAllBySenderId(id)
            .map {
                requestCodec.decode(it)
            }
    }
}
