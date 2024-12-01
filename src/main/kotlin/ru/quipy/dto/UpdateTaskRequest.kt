package ru.quipy.dto

import java.util.UUID

data class UpdateTaskRequest (
    val userId: UUID,
    val projectId: UUID,
    val taskId: UUID,
    val newTitle: String,
    val newDescription: String
)