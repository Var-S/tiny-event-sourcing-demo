package ru.quipy.Dtos

import java.util.*

data class TaskCreateRequest(
    val projectId: UUID,
    val taskName: String,
    val description: String,
    val status: String,
    val assignerLogin: String
)