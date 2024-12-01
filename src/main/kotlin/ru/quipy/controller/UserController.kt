package ru.quipy.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.quipy.dto.UserCreateRequest
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.api.UserLoginUpdatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.UserAggregateState
import ru.quipy.logic.create
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {

    @GetMapping("/{userId}")
    fun getUserById(@PathVariable userId: UUID): UserAggregateState? {
        return userEsService.getState(userId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userCreateRequest: UserCreateRequest): UserCreatedEvent {
        return userEsService.create { it.create(UUID.randomUUID(), userCreateRequest.login) }

    }

    @PutMapping("/{id}/login")
    fun updateLogin(@PathVariable id: UUID, @RequestBody request: UserCreateRequest): UserLoginUpdatedEvent {
        return userEsService.update(id) { userState ->
            userState.updateLogin(request.login)
        }
        "Login updated successfully"
    }
}
