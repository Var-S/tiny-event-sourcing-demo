package ru.quipy.dto

import java.util.*

data class AddStatusRequest (
    val userId: UUID,
    val projectId: UUID,
    val status: String,
)