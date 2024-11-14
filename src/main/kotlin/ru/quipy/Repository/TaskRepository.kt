package ru.quipy.Repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.Models.TaskViewDomain
import java.util.*

interface TaskRepository : MongoRepository<TaskViewDomain.Task, UUID> {
    override fun findById(taskId: UUID): Optional<TaskViewDomain.Task>
}