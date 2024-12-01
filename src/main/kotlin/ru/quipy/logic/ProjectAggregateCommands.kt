package ru.quipy.logic

import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import ru.quipy.api.*
import java.util.*

fun ProjectAggregateState.create(creatorId: UUID, title: String): ProjectCreatedEvent {
    return ProjectCreatedEvent(
        projectId = UUID.randomUUID(),
        title = title,
        creatorId = creatorId,
    )
}

fun ProjectAggregateState.update(userId: UUID, newTitle: String): ProjectUpdatedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }

    return ProjectUpdatedEvent(
        projectId = getId(),
        title = newTitle,
    )
}

fun ProjectAggregateState.addStatus(userId: UUID, status: String): StatusAddedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }
    if (statuses.contains(status)) {
        throw ResponseStatusException(HttpStatus.CONFLICT, "status: $status already exists")
    }

    return StatusAddedEvent(
        projectId = getId(),
        status = status,
    )
}

fun ProjectAggregateState.deleteStatus(userId: UUID, deletedStatus: String): StatusDeletedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }
    if (deletedStatus == "CREATED") {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "can't deleted CREATED status")
    }
    if (!statuses.contains(deletedStatus)) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "status: $deletedStatus does not exists")
    }
    if (tasks.any{ it.value.status == deletedStatus }) {
        throw ResponseStatusException(HttpStatus.CONFLICT, "task with status: $deletedStatus exists")
    }

    return StatusDeletedEvent(
        projectId = getId(),
        deletedStatus = deletedStatus,
    )
}

fun ProjectAggregateState.addParticipant(userId: UUID, participantId: UUID): ParticipantAddedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }
    if (participantsIds.contains(participantId)) {
        throw ResponseStatusException(HttpStatus.BAD_REQUEST, "user: $participantId already participant")
    }

    return ParticipantAddedEvent(
        projectId = getId(),
        participantId = participantId,
    )
}

fun ProjectAggregateState.createTask(userId: UUID, taskName: String, taskDescription: String, ): TaskCreatedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }

    return TaskCreatedEvent(
        projectId = getId(),
        taskId = UUID.randomUUID(),
        taskName = taskName,
        description = taskDescription,
    )
}

fun ProjectAggregateState.deleteTask(userId: UUID, taskId: UUID): TaskDeletedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }
    if (!tasks.containsKey(taskId)) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "no task: $taskId in project")
    }

    return TaskDeletedEvent(projectId = getId(), taskId = taskId)
}

fun ProjectAggregateState.updateTask(
    userId: UUID,
    taskId: UUID,
    newTitle: String,
    newDescription: String,
): TaskUpdatedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }
    if (!tasks.containsKey(taskId)) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "no task: $taskId in project")
    }

    return TaskUpdatedEvent(projectId = getId(), taskId = taskId, newTitle = newTitle, newDescription = newDescription)
}

fun ProjectAggregateState.changeTaskStatus(
    userId: UUID,
    taskId: UUID,
    newStatus: String,
): TaskStatusChangedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }
    if (!tasks.containsKey(taskId)) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "no task: $taskId in project")
    }
    if (!statuses.contains(newStatus)) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "status: $newStatus does not exists in project")
    }

    return TaskStatusChangedEvent(projectId = getId(), taskId = taskId, newStatus = newStatus)
}

fun ProjectAggregateState.addTaskPerformer(
    userId: UUID,
    taskId: UUID,
    performerId: UUID,
): TaskPerformerAddedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }
    if (!tasks.containsKey(taskId)) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "no task: $taskId in project")
    }
    if (!participantsIds.contains(performerId)) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "performer: $performerId is not member of a project")
    }
    if (tasks[taskId]!!.performersIds.contains(performerId)) {
        throw ResponseStatusException(HttpStatus.CONFLICT, "user: $performerId already performer of a task: $taskId")
    }
    return TaskPerformerAddedEvent(projectId = getId(), taskId = taskId, performerId = performerId)
}

fun ProjectAggregateState.deleteTaskPerformer(
    userId: UUID,
    taskId: UUID,
    performerId: UUID,
): TaskPerformerDeletedEvent {
    if (!participantsIds.contains(userId)) {
        throw ResponseStatusException(HttpStatus.FORBIDDEN, "user: $userId is not member of a project")
    }
    if (!tasks.containsKey(taskId)) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "no task: $taskId in project")
    }
    if (!tasks[taskId]!!.performersIds.contains(performerId)) {
        throw ResponseStatusException(HttpStatus.NOT_FOUND, "user: $performerId is not performer of a task: $taskId")
    }
    return TaskPerformerDeletedEvent(projectId = getId(), taskId = taskId, performerId = performerId)
}