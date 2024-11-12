package ru.quipy.Repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.Models.ProjectViewDomain
import java.util.UUID

interface ProjectRepository : MongoRepository<ProjectViewDomain.Project, UUID> {
    fun findByTitle(title: String): ProjectViewDomain.Project?
    fun findByCreatorId(creatorId: String): List<ProjectViewDomain.Project>
}
