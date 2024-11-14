package ru.quipy.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.quipy.Dtos.TaskCreateRequest
import ru.quipy.Models.TaskViewDomain
import ru.quipy.Service.TaskViewService
import ru.quipy.api.TaskCreateEvent
import java.util.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskViewService: TaskViewService
) {

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTaskById(@PathVariable taskId: UUID): TaskViewDomain.Task? {
        return taskViewService.findById(taskId)
    }

    @GetMapping("/project/{projectId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTasksByProjectId(@PathVariable projectId: UUID): Optional<TaskViewDomain.Task> {
        return taskViewService.findByProjectId(projectId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@RequestBody taskCreateRequest: TaskCreateRequest): TaskViewDomain.Task {
        val event = TaskCreateEvent(
            taskId = UUID.randomUUID(),
            projectId = taskCreateRequest.projectId,
            taskName = taskCreateRequest.taskName,
            description = taskCreateRequest.description,
            status = taskCreateRequest.status,
            assignerLogin = taskCreateRequest.assignerLogin
        )
        taskViewService.saveTask(event)
        return TaskViewDomain.Task(event.taskId, event.taskName, event.status, event.description)
    }

    @PutMapping("/{taskId}/title")
    fun updateTaskTitle(@PathVariable taskId: UUID, @RequestBody newTitle: String): ResponseEntity<TaskViewDomain.Task> {
        val updatedTask = taskViewService.updateTaskTitle(taskId, newTitle)
        return if (updatedTask != null) {
            ResponseEntity.ok(updatedTask)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{taskId}/status")
    fun changeTaskStatus(@PathVariable taskId: UUID, @RequestBody newStatus: String): ResponseEntity<TaskViewDomain.Task> {
        val updatedTask = taskViewService.changeStatus(taskId, newStatus)
        return if (updatedTask != null) {
            ResponseEntity.ok(updatedTask)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{taskId}/assign")
    fun assignUserToTask(@PathVariable taskId: UUID, @RequestBody executorId: UUID): ResponseEntity<TaskViewDomain.Task> {
        val updatedTask = taskViewService.assignUserToTask(taskId, executorId)
        return if (updatedTask != null) {
            ResponseEntity.ok(updatedTask)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PutMapping("/{taskId}/unassign")
    fun removeUserFromTask(@PathVariable taskId: UUID, @RequestBody executorId: UUID): ResponseEntity<TaskViewDomain.Task> {
        val updatedTask = taskViewService.removeUserFromTask(taskId, executorId)
        return if (updatedTask != null) {
            ResponseEntity.ok(updatedTask)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{taskId}")
    fun deleteTask(@PathVariable taskId: UUID): ResponseEntity<Void> {
        return if (taskViewService.deleteTask(taskId)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}