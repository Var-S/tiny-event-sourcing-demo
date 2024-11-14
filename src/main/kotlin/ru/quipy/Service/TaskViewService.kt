package ru.quipy.Service

import org.springframework.stereotype.Service
import ru.quipy.Models.TaskViewDomain
import ru.quipy.Repository.TaskRepository
import ru.quipy.api.TaskAggregate
import ru.quipy.api.TaskCreateEvent
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent
import java.util.*

@Service
@AggregateSubscriber(aggregateClass = TaskAggregate::class, subscriberName = "task-data-sub")
class TaskViewService(
    private val taskRepository: TaskRepository
) {

    @SubscribeEvent
    fun saveTask(event: TaskCreateEvent) {
        taskRepository.save(
            TaskViewDomain.Task(event.taskId, event.taskName, event.status, event.description)
        )
    }

    fun changeStatus(taskId: UUID, newStatus: String): TaskViewDomain.Task? {
        val task = taskRepository.findById(taskId).orElse(null)
        return if (task != null) {
            task.status = newStatus
            taskRepository.save(task)
        } else {
            null
        }
    }

    fun updateTaskTitle(taskId: UUID, newTitle: String): TaskViewDomain.Task? {
        val task = taskRepository.findById(taskId).orElse(null)
        return if (task != null) {
            task.taskName = newTitle
            taskRepository.save(task)
        } else {
            null
        }
    }

    fun assignUserToTask(taskId: UUID, executorId: UUID): TaskViewDomain.Task? {
        val task = taskRepository.findById(taskId).orElse(null)
        return if (task != null) {
            taskRepository.save(task)
        } else {
            null
        }
    }

    fun removeUserFromTask(taskId: UUID, executorId: UUID): TaskViewDomain.Task? {
        val task = taskRepository.findById(taskId).orElse(null)
        return if (task != null) {
            taskRepository.save(task)
        } else {
            null
        }
    }

    fun findById(taskId: UUID): TaskViewDomain.Task? {
        return taskRepository.findById(taskId).orElse(null)
    }

    fun findByProjectId(projectId: UUID): Optional<TaskViewDomain.Task> {
        return taskRepository.findById(projectId)
    }

    fun deleteTask(taskId: UUID): Boolean {
        val task = taskRepository.findById(taskId).orElse(null)
        return if (task != null) {
            taskRepository.delete(task)
            true
        } else {
            false
        }
    }
}