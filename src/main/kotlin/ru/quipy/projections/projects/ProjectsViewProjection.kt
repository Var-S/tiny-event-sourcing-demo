package ru.quipy.projections.projects

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class ProjectsViewProjection {
    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    lateinit var projectRepository: ProjectsProjectionRepository

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(ProjectAggregate::class, "projects-subscriber") {
            `when`(ProjectCreatedEvent::class) {
                projectRepository.save(ProjectEntity(
                    id = it.id,
                    title = it.title,
                ))
            }

            `when`(ProjectUpdatedEvent::class) {
                val project = projectRepository.getReferenceById(it.projectId)
                project.title = it.title
                projectRepository.save(project)
            }

            `when`(StatusAddedEvent::class) {
                val project = projectRepository.getReferenceById(it.projectId)
                project.statuses.add(it.status)
                projectRepository.save(project)
            }

            `when`(StatusDeletedEvent::class) {
                val project = projectRepository.getReferenceById(it.projectId)
                project.statuses.remove(it.deletedStatus)
                projectRepository.save(project)
            }

            `when`(ParticipantAddedEvent::class) {
                val project = projectRepository.getReferenceById(it.projectId)
                project.participants.add(it.participantId)
                projectRepository.save(project)
            }
        }
    }
}