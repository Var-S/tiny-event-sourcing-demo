package ru.quipy.dto

import java.util.*

data class ProjectResponse (
    val projectId: UUID = UUID.randomUUID(),
    val title: String = "",
    val statuses: List<String> = listOf(),
    val participantsIds: List<UUID> = listOf(),
)