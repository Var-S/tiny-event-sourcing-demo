package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

// Service's business logic
class ProjectAggregateState : AggregateState<UUID, ProjectAggregate> {
    private lateinit var projectId: UUID
    private var createdAt: Long = System.currentTimeMillis()
    private var updatedAt: Long = System.currentTimeMillis()

    private lateinit var projectTitle: String
    var statuses = mutableListOf("CREATED")
    var participantsIds = mutableSetOf<UUID>()
    var tasks = mutableMapOf<UUID, TaskEntity>()

    override fun getId() = projectId

    @StateTransitionFunc
    fun projectCreatedApply(event: ProjectCreatedEvent) {
        projectId = event.projectId
        projectTitle = event.title
        participantsIds.add(event.creatorId)
        createdAt = event.createdAt
    }

    @StateTransitionFunc
    fun projectUpdatedApply(event: ProjectUpdatedEvent) {
        projectTitle = event.title
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun participantAddedApply(event: ParticipantAddedEvent) {
        participantsIds.add(event.participantId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun statusAddedApply(event: StatusAddedEvent) {
        statuses.add(event.status)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun statusDeletedApply(event: StatusDeletedEvent) {
        statuses.removeIf{ it == event.deletedStatus }
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreatedEvent) {
        tasks[event.taskId] = TaskEntity(
            event.taskId,
            event.taskName,
            event.description,
            "CREATED",
            mutableSetOf(),
        )
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskDeletedApply(event: TaskDeletedEvent) {
        tasks.remove(event.taskId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskUpdatedApply(event: TaskUpdatedEvent) {
        tasks[event.taskId]?.title = event.newTitle
        tasks[event.taskId]?.description = event.newDescription
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskStatusChangedApply(event: TaskStatusChangedEvent) {
        tasks[event.taskId]?.status = event.newStatus
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskPerformerAddedApply(event: TaskPerformerAddedEvent) {
        tasks[event.taskId]?.performersIds?.add(event.performerId)
        updatedAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskPerformerDeletedApply(event: TaskPerformerDeletedEvent) {
        tasks[event.taskId]?.performersIds?.remove(event.performerId)
        updatedAt = event.createdAt
    }
}

class TaskEntity(
    var id: UUID,
    var title: String,
    var description: String,
    var status: String,
    var performersIds: MutableSet<UUID>,
)
