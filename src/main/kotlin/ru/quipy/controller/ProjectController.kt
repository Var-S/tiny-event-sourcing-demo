package ru.quipy.controller

import org.springframework.web.bind.annotation.*
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.dto.*
import ru.quipy.logic.*
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>,
    private val userEsService: EventSourcingService<UUID, UserAggregate, UserAggregateState>
) {
    @PostMapping("/create")
    fun createProject(@RequestBody request: ProjectCreateRequest): ProjectCreatedEvent {
        // TODO добавить проверку на существование пользователя

        return projectEsService.create {
            it.create(request.creatorId, request.projectTitle)
        }
    }

    @PutMapping("/update")
    fun updateProject(@RequestBody request: ProjectUpdateRequest): ProjectUpdatedEvent {
        return projectEsService.update(request.projectId) {
            it.update(request.userId, request.newTitle)
        }
    }

    @PutMapping("/add-participant")
    fun addParticipant(@RequestBody request: AddParticipantRequest): ParticipantAddedEvent {
        // TODO добавить проверку на существование участника
        return projectEsService.update(request.projectId) {
            it.addParticipant(request.userId, request.participantId)
        }
    }

    @PostMapping("/add-status")
    fun addStatus(@RequestBody request: AddStatusRequest): StatusAddedEvent {
        return projectEsService.update(request.projectId) {
            it.addStatus(request.userId, request.status)
        }
    }

    @DeleteMapping("/delete-status")
    fun deleteStatus(@RequestBody request: DeleteStatusRequest): StatusDeletedEvent {
        return projectEsService.update(request.projectId) {
            it.deleteStatus(request.userId, request.status)
        }
    }

    @PostMapping("/create-task")
    fun createTask(@RequestBody request: TaskCreateRequest): TaskCreatedEvent {
        return projectEsService.update(request.projectId) {
            it.createTask(request.creatorId, request.taskName, request.taskDescription)
        }
    }

    @DeleteMapping("/delete-task")
    fun deleteTask(@RequestBody request: DeleteTaskRequest): TaskDeletedEvent {
        return projectEsService.update(request.projectId) {
            it.deleteTask(request.userId, request.taskId)
        }
    }

    @PutMapping("/update-task")
    fun updateTask(@RequestBody request: UpdateTaskRequest): TaskUpdatedEvent {
        return projectEsService.update(request.projectId) {
            it.updateTask(
                userId = request.userId,
                taskId = request.taskId,
                newTitle = request.newTitle,
                newDescription = request.newDescription,
            )
        }
    }

    @PutMapping("/change-task-status")
    fun changeTaskStatus(@RequestBody request: ChangeTaskStatusRequest): TaskStatusChangedEvent {
        return projectEsService.update(request.projectId) {
            it.changeTaskStatus(userId = request.userId, taskId = request.taskId, newStatus = request.newStatus)
        }
    }

    @PutMapping("/add-task-performer")
    fun addTaskPerformer(@RequestBody request: AddTaskPerformerRequest): TaskPerformerAddedEvent {
        return projectEsService.update(request.projectId) {
            it.addTaskPerformer(userId = request.userId, taskId = request.taskId, performerId = request.performerId)
        }
    }

    @PutMapping("/add-task-performer")
    fun deletedTaskPerformer(@RequestBody request: DeleteTaskPerformerRequest): TaskPerformerDeletedEvent {
        return projectEsService.update(request.projectId) {
            it.deleteTaskPerformer(userId = request.userId, taskId = request.taskId, performerId = request.performerId)
        }
    }
}
