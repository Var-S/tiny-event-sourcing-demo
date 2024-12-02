package ru.quipy.projections.projects

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "projects")
data class ProjectEntity(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "title", nullable = false)
    val title: String = "",

    @ElementCollection
    @CollectionTable(name = "project_statuses", joinColumns = [JoinColumn(name = "project_id")])
    @Column(name = "status")
    val statuses: List<String> = listOf(),

    @ElementCollection
    @CollectionTable(name = "project_participants", joinColumns = [JoinColumn(name = "project_id")])
    @Column(name = "participant_id")
    val participants: List<UUID> = listOf()
)