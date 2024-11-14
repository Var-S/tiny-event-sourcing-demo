package ru.quipy.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate
import java.util.*

@AggregateType(aggregateEventsTableName = "aggregate-task")
class TaskAggregate : Aggregate {

    fun createTask(taskId: UUID, projectId: UUID, taskName: String, description: String, status: String, assignerLogin: String): TaskCreateEvent {
        return TaskCreateEvent(taskId, projectId, taskName, description, status, assignerLogin)
    }

    fun updateTask(taskId: UUID, newTitle: String): TaskUpdatedEvent {
        return TaskUpdatedEvent(taskId, newTitle)
    }

    fun changeStatus(taskId: UUID, newStatus: String): StatusChangedEvent {
        return StatusChangedEvent(taskId, newStatus)
    }

    fun assignUserToTask(taskId: UUID, executorId: UUID): AssignUserToTaskEvent {
        return AssignUserToTaskEvent(taskId, executorId)
    }

    fun deleteUserFromTask(taskId: UUID, executorId: UUID): DeleteUserFromTaskEvent {
        return DeleteUserFromTaskEvent(taskId, executorId)
    }

    fun deleteTask(projectId: UUID, taskId: UUID): TaskDeletedEvent {
        return TaskDeletedEvent(projectId, taskId)
    }
}