package ru.quipy.dto

import java.util.UUID

data class AddParticipantRequest (
    val userId: UUID,
    val projectId: UUID,
    val participantId: UUID,
)