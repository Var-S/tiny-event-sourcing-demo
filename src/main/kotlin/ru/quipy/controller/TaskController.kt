package ru.quipy.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import ru.quipy.Dtos.TaskCreateRequest
import ru.quipy.api.TaskAggregate
import ru.quipy.api.TaskCreateEvent
import ru.quipy.api.TaskUpdatedEvent
import ru.quipy.core.EventSourcingService
import ru.quipy.logic.TaskAggregateState
import ru.quipy.logic.create
import ru.quipy.logic.updateTaskTitle
import java.util.*

@RestController
@RequestMapping("/api/tasks")
class TaskController(
    private val taskEsService : EventSourcingService<UUID, TaskAggregate, TaskAggregateState>
) {

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    fun getTaskById(@PathVariable taskId: UUID): TaskAggregateState? {
        return taskEsService.getState(taskId)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createTask(@RequestBody taskCreateRequest: TaskCreateRequest): TaskCreateEvent {
        return taskEsService.create { it.create(UUID.randomUUID(), taskCreateRequest.projectId, taskCreateRequest.taskName, taskCreateRequest.description, taskCreateRequest.status, taskCreateRequest.assignerLogin) }
    }

    @PutMapping("/{taskId}/title")
    fun updateTaskTitle(@PathVariable taskId: UUID, @RequestBody newTitle: String): TaskUpdatedEvent {
        return taskEsService.update(taskId) {taskState ->
            taskState.updateTaskTitle(taskId, newTitle)
        }
    }
}