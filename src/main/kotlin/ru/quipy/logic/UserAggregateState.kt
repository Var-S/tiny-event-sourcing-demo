package ru.quipy.logic

import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.api.UserLoginUpdatedEvent
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.UUID

class UserAggregateState : AggregateState<UUID, UserAggregate> {
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()
    private lateinit var userId: UUID

    lateinit var login: String

    override fun getId() = userId

    @StateTransitionFunc
    fun userCreatedApply(event: UserCreatedEvent) {
        userId = event.userId
        login = event.login
    }

    fun updateLogin(newLogin: String): UserLoginUpdatedEvent {
        return UserLoginUpdatedEvent(userId, newLogin)
    }

    @StateTransitionFunc
    fun userLoginUpdatedApply(event: UserLoginUpdatedEvent) {
        login = event.newLogin
        updatedAt = System.currentTimeMillis()
    }
}