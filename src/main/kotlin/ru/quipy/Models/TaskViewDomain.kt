package ru.quipy.Models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.*

class TaskViewDomain {

    @Document("tasks-view")
    data class Task(
        @Id
        override val id: UUID,
        var taskName: String,
        var status: String,
        val description: String
    ) : Unique<UUID>
}