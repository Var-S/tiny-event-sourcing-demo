package ru.quipy.logic

import ru.quipy.api.UserCreatedEvent
import java.util.*

fun UserAggregateState.create(id: UUID, userLogin: String): UserCreatedEvent {
    return UserCreatedEvent(
        userId = id,
        login = userLogin
    )
}