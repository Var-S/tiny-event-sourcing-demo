package ru.quipy.dto

import java.util.UUID

data class AddTaskPerformerRequest (
    val userId: UUID,
    val projectId: UUID,
    val taskId: UUID,
    val performerId: UUID,
)