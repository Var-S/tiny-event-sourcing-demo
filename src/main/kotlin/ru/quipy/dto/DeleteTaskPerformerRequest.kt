package ru.quipy.dto

import java.util.UUID

data class DeleteTaskPerformerRequest (
    val userId: UUID,
    val projectId: UUID,
    val taskId: UUID,
    val performerId: UUID,
)