package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val PROJECT_CREATED_EVENT = "PROJECT_CREATED_EVENT"
const val TAG_CREATED_EVENT = "TAG_CREATED_EVENT"
const val TAG_ASSIGNED_TO_TASK_EVENT = "TAG_ASSIGNED_TO_TASK_EVENT"
const val TASK_CREATED_EVENT = "TASK_CREATED_EVENT"
const val PROJECT_UPDATED_EVENT = "PROJECT_UPDATED_EVENT"

// API
@DomainEvent(name = PROJECT_CREATED_EVENT)
class ProjectCreatedEvent(
    val projectId: UUID,
    val title: String,
    val creatorId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_CREATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = PROJECT_UPDATED_EVENT)
class ProjectUpdatedEvent(
    val projectId: UUID,
    val title: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PROJECT_UPDATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = STATUS_ADDED_EVENT)
class StatusAddedEvent(
    val projectId: UUID,
    val status: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = STATUS_ADDED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = STATUS_DELETED_EVENT)
class StatusDeletedEvent(
    val projectId: UUID,
    val deletedStatus: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = STATUS_DELETED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_CREATED_EVENT)
class TaskCreatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val taskName: String,
    val description: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_CREATED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_DELETED_EVENT)
class TaskDeletedEvent(
    val projectId: UUID,
    val taskId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_DELETED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = TASK_UPDATED_EVENT)
class TaskUpdatedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val newTitle: String,
    val newDescription: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = TASK_UPDATED_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = STATUS_CHANGED_EVENT)
class TaskStatusChangedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val newStatus: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = STATUS_CHANGED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = PARTICIPANT_ADDED_EVENT)
class ParticipantAddedEvent(
    val projectId: UUID,
    val participantId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PARTICIPANT_ADDED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = PERFORMER_ADDED_EVENT)
class TaskPerformerAddedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val performerId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PERFORMER_ADDED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = PERFORMER_DELETED_EVENT)
class TaskPerformerDeletedEvent(
    val projectId: UUID,
    val taskId: UUID,
    val performerId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<ProjectAggregate>(
    name = PERFORMER_DELETED_EVENT,
    createdAt = createdAt
)

