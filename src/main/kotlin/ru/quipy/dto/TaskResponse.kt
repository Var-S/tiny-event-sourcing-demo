package ru.quipy.dto

import java.util.*

data class TaskResponse (
    val taskId: UUID = UUID.randomUUID(),
    val projectId: UUID = UUID.randomUUID(),
    val title: String = "",
    val description: String = "",
    val status: String = "",
    val performersIds: List<UUID> = emptyList(),
)