package ru.quipy.dto

import java.util.UUID

data class UserUpdateRequest (
    val userId: UUID,
    val newLogin: String,
)