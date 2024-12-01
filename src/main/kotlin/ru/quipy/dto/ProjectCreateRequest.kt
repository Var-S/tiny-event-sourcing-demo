package ru.quipy.dto

import java.util.*

data class ProjectCreateRequest (
    val creatorId: UUID,
    val projectTitle: String,
)