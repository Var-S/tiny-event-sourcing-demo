package ru.quipy.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.quipy.dto.UserCreateRequest
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.api.UserUpdatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.dto.UserResponse
import ru.quipy.dto.UserUpdateRequest
import ru.quipy.logic.UserAggregateState
import ru.quipy.logic.create
import ru.quipy.logic.update
import ru.quipy.projections.users.UsersViewProjection
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>,
    val usersProjection: UsersViewProjection,
) {
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody userCreateRequest: UserCreateRequest): UserCreatedEvent {
        return userEsService.create {
            it.create(userCreateRequest.login)
        }
    }

    @PutMapping
    fun update(@RequestBody request: UserUpdateRequest): UserUpdatedEvent {
        return userEsService.update(request.userId) {
            it.update(request.newLogin)
        }
    }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): UserResponse {
        val user = usersProjection.getUserById(id)
        return UserResponse(
            id = user.id,
            login = user.login,
        )
    }

    @GetMapping
    fun getAll(): List<UserResponse> {
        val users = usersProjection.getAllUsers()
        return users.map {
            UserResponse(
                id = it.id,
                login = it.login,
            )
        }
    }
}
