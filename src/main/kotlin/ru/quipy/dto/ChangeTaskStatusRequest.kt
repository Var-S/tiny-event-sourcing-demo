package ru.quipy.dto

import java.util.*

data class ChangeTaskStatusRequest (
    val userId: UUID,
    val projectId: UUID,
    val taskId: UUID,
    val newStatus: String,
)