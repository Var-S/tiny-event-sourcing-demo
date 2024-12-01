package ru.quipy.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.quipy.dto.UserCreateRequest
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.api.UserUpdatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.dto.UserUpdateRequest
import ru.quipy.logic.UserAggregateState
import ru.quipy.logic.create
import ru.quipy.logic.update
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userCreateRequest: UserCreateRequest): UserCreatedEvent {
        return userEsService.create {
            it.create(userCreateRequest.login)
        }
    }

    @PutMapping
    fun updateLogin(@RequestBody request: UserUpdateRequest): UserUpdatedEvent {
        return userEsService.update(request.userId) {
            it.update(request.newLogin)
        }
    }
}
