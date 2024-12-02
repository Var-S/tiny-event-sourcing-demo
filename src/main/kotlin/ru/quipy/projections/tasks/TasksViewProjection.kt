package ru.quipy.projections.tasks

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.streams.AggregateSubscriptionsManager
import java.util.*
import javax.annotation.PostConstruct

@Service
class TasksViewProjection {
    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    lateinit var taskRepository: TasksProjectionRepository

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "tasks-subscriber") {
            `when`(TaskCreatedEvent::class) {
                taskRepository.save(TaskEntity(
                    id = it.taskId,
                    projectId = it.projectId,
                    title = it.taskName,
                ))
            }

            `when`(TaskDeletedEvent::class) {
                taskRepository.deleteById(it.taskId)
            }

            `when`(TaskUpdatedEvent::class) {
                val task = taskRepository.getReferenceById(it.taskId)
                task.title = it.newTitle
                task.description = it.newDescription
                taskRepository.save(task)
            }

            `when`(TaskStatusChangedEvent::class) {
                val task = taskRepository.getReferenceById(it.taskId)
                task.status = it.newStatus
                taskRepository.save(task)
            }

            `when`(TaskPerformerAddedEvent::class) {
                val task = taskRepository.getReferenceById(it.taskId)
                task.performersIds.add(it.performerId)
                taskRepository.save(task)
            }

            `when`(TaskPerformerDeletedEvent::class) {
                val task = taskRepository.getReferenceById(it.taskId)
                task.performersIds.remove(it.performerId)
                taskRepository.save(task)
            }
        }
    }

    fun getTaskById(id: UUID) : TaskEntity {
        return taskRepository.getReferenceById(id)
    }

    fun getTasksByProjectId(projectId: UUID): List<TaskEntity> {
        val tasks = taskRepository.findAll()
        return tasks.filter { it.projectId == projectId }
    }
}