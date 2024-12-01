package ru.quipy.dto

import java.util.*

data class DeleteStatusRequest (
    val userId: UUID,
    val projectId: UUID,
    val status: String,
)