package ru.quipy.api

import ru.quipy.core.annotations.DomainEvent
import ru.quipy.domain.Event
import java.util.*

const val TASK_CREATE_EVENT = "TASK_CREATED_EVENT"
const val STATUS_CHANGED_EVENT = "STATUS_CHANGED_EVENT"
const val ASSIGN_USER_TO_TASK_EVENT = "ASSIGN_USER_TO_TASK_EVENT"
const val DELETE_USER_FROM_TASK_EVENT = "DELETE_USER_FROM_TASK_EVENT"
const val TASK_DELETED_EVENT = "TASK_DELETED_EVENT"

// API
@DomainEvent(name = TASK_CREATE_EVENT)
class TaskCreateEvent(
    val taskId: UUID,
    val projectId: UUID,
    val taskName: String,
    val description: String,
    val status: String,
    val assignerLogin: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = TASK_CREATE_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = STATUS_CHANGED_EVENT)
class StatusChangedEvent(
    val taskId: UUID,
    val status: String,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = STATUS_CHANGED_EVENT,
    createdAt = createdAt
)

@DomainEvent(name = ASSIGN_USER_TO_TASK_EVENT)
class AssignUserToTaskEvent(
    val taskId: UUID,
    val executorId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = ASSIGN_USER_TO_TASK_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = DELETE_USER_FROM_TASK_EVENT)
class DeleteUserFromTaskEvent(
    val taskId: UUID,
    val executorId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = DELETE_USER_FROM_TASK_EVENT,
    createdAt = createdAt,
)

@DomainEvent(name = TASK_DELETED_EVENT)
class TaskDeletedEvent(
    val projectId: UUID,
    val taskId: UUID,
    createdAt: Long = System.currentTimeMillis(),
) : Event<TaskAggregate>(
    name = TASK_DELETED_EVENT,
    createdAt = createdAt,
)
