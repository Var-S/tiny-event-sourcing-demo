package ru.quipy.Models

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import ru.quipy.domain.Unique
import java.util.UUID

class ProjectViewDomain {

    @Document("projects-view")
    data class Project(
        @Id
        override val id: UUID,
        var title: String,
        val creatorId: String,
        val taskIds: List<UUID> = emptyList()
    ) : Unique<UUID>
}
