package ru.quipy.dto

import java.util.*

data class UserResponse (
    val id: UUID = UUID.randomUUID(),
    val login: String = "",
)