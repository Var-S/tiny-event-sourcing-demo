package ru.quipy.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import ru.quipy.api.*
import ru.quipy.core.EventSourcingService
import ru.quipy.dto.*
import ru.quipy.logic.*
import ru.quipy.projections.projects.ProjectsViewProjection
import ru.quipy.projections.tasks.TasksViewProjection
import ru.quipy.projections.users.UsersViewProjection
import java.util.*

@RestController
@RequestMapping("/projects")
class ProjectController(
    val projectEsService: EventSourcingService<UUID, ProjectAggregate, ProjectAggregateState>,
    val usersProjection: UsersViewProjection,
    val taskProjection: TasksViewProjection,
    val projectProjection: ProjectsViewProjection,
) {
    @PostMapping("/create")
    fun createProject(@RequestBody request: ProjectCreateRequest): ProjectCreatedEvent {
        val user = usersProjection.findUserById(request.creatorId)
        if (user.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "user: ${request.creatorId} does not exist")
        }

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
        val participant = usersProjection.findUserById(request.participantId)
        if (participant.isEmpty) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "user: ${request.participantId} does not exist")
        }

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

    @GetMapping("/get-by-id/{id}")
    fun getProjectById(@PathVariable id: UUID): ProjectResponse {
        val project = projectProjection.getProjectById(id)
        return ProjectResponse(
            projectId = project.id,
            title = project.title,
            statuses = project.statuses,
            participantsIds = project.participants,
        )
    }

    @GetMapping("/get-users-projects/{userId}")
    fun getUsersProject(@PathVariable userId: UUID): List<ProjectResponse> {
        val projects = projectProjection.getUsersProjects(userId)
        return projects.map { ProjectResponse(
            projectId = it.id,
            title = it.title,
            statuses = it.statuses,
            participantsIds = it.participants,
        )}
    }

    @GetMapping("/get-task-by-id/{taskId}")
    fun getTaskById(@PathVariable taskId: UUID): TaskResponse {
        val task = taskProjection.getTaskById(taskId)
        return TaskResponse(
            taskId = task.id,
            projectId = task.projectId,
            title = task.title,
            description = task.description,
            status = task.status,
            performersIds = task.performersIds,
        )
    }

    @GetMapping("/get-tasks-by-project-id/{projectId}")
    fun getTasksByProjectId(@PathVariable projectId: UUID): List<TaskResponse> {
        val tasks = taskProjection.getTasksByProjectId(projectId)
        return tasks.map { TaskResponse(
            taskId = it.id,
            projectId = it.projectId,
            title = it.title,
            description = it.description,
            status = it.status,
            performersIds = it.performersIds,
        )}
    }
}
