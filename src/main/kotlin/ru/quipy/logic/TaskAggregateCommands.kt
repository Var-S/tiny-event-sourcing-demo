package ru.quipy.logic

import ru.quipy.api.*
import java.util.*

fun TaskAggregateState.create(taskId: UUID, projectId: UUID, taskName: String, description: String, status: String, assignerLogin: String): TaskCreateEvent {
    return TaskCreateEvent(
        taskId = taskId,
        projectId = projectId,
        taskName = taskName,
        description = description,
        status = status,
        assignerLogin = assignerLogin
    )
}

fun TaskAggregateState.updateTaskTitle(taskId: UUID, newTitle: String): TaskUpdatedEvent {
    if (this.taskId != taskId) {
        throw IllegalArgumentException("Task ID mismatch: ${this.taskId} != $taskId")
    }

    return TaskUpdatedEvent(
        taskId = taskId,
        newTitle = newTitle
    )
}

fun TaskAggregateState.changeStatus(taskId: UUID, newStatus: String): StatusChangedEvent {
    if (this.taskId != taskId) {
        throw IllegalArgumentException("Task ID mismatch: ${this.taskId} != $taskId")
    }

    return StatusChangedEvent(
        taskId = taskId,
        status = newStatus
    )
}

fun TaskAggregateState.assignUserToTask(taskId: UUID, executorId: UUID): AssignUserToTaskEvent {
    if (this.taskId != taskId) {
        throw IllegalArgumentException("Task ID mismatch: ${this.taskId} != $taskId")
    }

    return AssignUserToTaskEvent(
        taskId = taskId,
        executorId = executorId
    )
}

fun TaskAggregateState.deleteUserFromTask(taskId: UUID, executorId: UUID): DeleteUserFromTaskEvent {
    if (this.taskId != taskId) {
        throw IllegalArgumentException("Task ID mismatch: ${this.taskId} != $taskId")
    }

    return DeleteUserFromTaskEvent(
        taskId = taskId,
        executorId = executorId
    )
}

fun TaskAggregateState.deleteTask(projectId: UUID, taskId: UUID): TaskDeletedEvent {
    if (this.taskId != taskId) {
        throw IllegalArgumentException("Task ID mismatch: ${this.taskId} != $taskId")
    }

    return TaskDeletedEvent(
        projectId = projectId,
        taskId = taskId
    )
}
