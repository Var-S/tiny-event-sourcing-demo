package ru.quipy.Service

import org.springframework.stereotype.Service
import ru.quipy.Models.ProjectViewDomain
import ru.quipy.Repository.ProjectRepository
import ru.quipy.api.ProjectAggregate
import ru.quipy.api.ProjectCreatedEvent
import ru.quipy.api.TaskCreatedEvent
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "project-data-sub"
)
class ProjectViewService(
    private val projectRepository: ProjectRepository
) {

    @SubscribeEvent
    fun saveProject(event: ProjectCreatedEvent) {
        projectRepository.save(
            ProjectViewDomain.Project(event.projectId, event.title, event.creatorId)
        )
    }
//
//    @SubscribeEvent
//    fun addTaskToProject(event: TaskCreatedEvent) {
//        val projectView = projectRepository.findById(event.projectId).get()
//        projectView. = projectView.taskIds + event.taskId
//        projectRepository.save(projectView)
//    }

    fun findByTitle(title: String): ProjectViewDomain.Project? {
        return projectRepository.findByTitle(title)
    }

    fun findByCreatorId(creatorId: String): List<ProjectViewDomain.Project> {
        return projectRepository.findByCreatorId(creatorId)
    }
}
