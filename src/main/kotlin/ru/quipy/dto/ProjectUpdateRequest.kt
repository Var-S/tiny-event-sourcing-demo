package ru.quipy.dto

import java.util.*

data class ProjectUpdateRequest (
    val userId: UUID,
    val projectId: UUID,
    val newTitle: String,
)