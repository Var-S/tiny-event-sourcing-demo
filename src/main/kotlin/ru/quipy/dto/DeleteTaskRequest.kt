package ru.quipy.dto

import java.util.*

data class DeleteTaskRequest (
    val userId: UUID,
    val projectId: UUID,
    val taskId: UUID,
)