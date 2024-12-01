package ru.quipy.logic

import ru.quipy.api.UserCreatedEvent
import ru.quipy.api.UserUpdatedEvent
import java.util.*

fun UserAggregateState.create(userLogin: String): UserCreatedEvent {
    return UserCreatedEvent(
        userId = UUID.randomUUID(),
        login = userLogin
    )
}

fun UserAggregateState.update(newLogin: String): UserUpdatedEvent {
    return UserUpdatedEvent(
        userId = getId(),
        newLogin = newLogin
    )
}