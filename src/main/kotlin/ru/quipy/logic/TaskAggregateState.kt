package ru.quipy.logic

import ru.quipy.api.*
import ru.quipy.core.annotations.StateTransitionFunc
import ru.quipy.domain.AggregateState
import java.util.*

class TaskAggregateState : AggregateState<UUID, TaskAggregate> {
    var createdAt: Long = System.currentTimeMillis()
    var updatedAt: Long = System.currentTimeMillis()
    lateinit var taskId: UUID

    lateinit var projectId: UUID
    lateinit var taskName: String
    lateinit var description: String
    lateinit var status: String
    var assignerLogin: String = ""
    var executorIds: MutableList<UUID> = mutableListOf()

    override fun getId() = taskId

    @StateTransitionFunc
    fun taskCreatedApply(event: TaskCreateEvent) {
        taskId = event.taskId
        projectId = event.projectId
        taskName = event.taskName
        description = event.description
        status = event.status
        assignerLogin = event.assignerLogin
        createdAt = event.createdAt
    }

    @StateTransitionFunc
    fun taskUpdatedApply(event: TaskUpdatedEvent) {
        taskName = event.newTitle
        updatedAt = System.currentTimeMillis()
    }

    @StateTransitionFunc
    fun statusChangedApply(event: StatusChangedEvent) {
        status = event.status
        updatedAt = System.currentTimeMillis()
    }

    @StateTransitionFunc
    fun assignUserToTaskApply(event: AssignUserToTaskEvent) {
        executorIds.add(event.executorId)
        updatedAt = System.currentTimeMillis()
    }

    @StateTransitionFunc
    fun deleteUserFromTaskApply(event: DeleteUserFromTaskEvent) {
        executorIds.remove(event.executorId)
        updatedAt = System.currentTimeMillis()
    }

    @StateTransitionFunc
    fun taskDeletedApply(event: TaskDeletedEvent) {
    }
}