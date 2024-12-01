package ru.quipy.dto

import java.util.*

data class TaskCreateRequest(
    val creatorId: UUID,
    val projectId: UUID,
    val taskName: String,
    val taskDescription: String,
)