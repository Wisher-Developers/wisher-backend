package ru.itmo.wisher.api.auth.infrastructure

import org.springframework.stereotype.Component
import ru.itmo.wisher.api.auth.domain.User
import ru.itmo.wisher.api.auth.infrastructure.entity.UserEntity

@Component
class UserCodec {

    fun encode(domain: User) =
        UserEntity(
            id = domain.id,
            username = domain.userName,
            password = domain.password,
            email = domain.email,
            avatar = domain.avatarLink,
        )

    fun decode(entity: UserEntity) =
        User(
            id = entity.id,
            userName = entity.username,
            password = entity.password,
            email = entity.email,
            avatarLink = entity.avatar,
        )
}
