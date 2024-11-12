package ru.quipy.controllers

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.quipy.Dtos.UserCreateRequest
import ru.quipy.Models.UserViewDomain
import ru.quipy.api.UserCreatedEvent
import ru.quipy.projections.UserViewService
import java.util.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userViewService: UserViewService
) {

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserById(@PathVariable userId: UUID): UserViewDomain.User? {
        return userViewService.findById(userId)
    }

    @GetMapping("/login/{login}")
    @ResponseStatus(HttpStatus.OK)
    fun getUserByLogin(@PathVariable login: String): UserViewDomain.User? {
        return userViewService.findByLogin(login)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody userCreateRequest: UserCreateRequest): UserViewDomain.User {
        val event = UserCreatedEvent(
            userId = UUID.randomUUID(),
            login = userCreateRequest.login,
        )
        userViewService.saveUser(event)
        return UserViewDomain.User(event.id, event.login)
    }
}
