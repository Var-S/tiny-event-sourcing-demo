package ru.quipy.projections.projects

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "projects")
data class ProjectEntity(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "title", nullable = false)
    var title: String = "",

    @ElementCollection
    @CollectionTable(name = "project_statuses", joinColumns = [JoinColumn(name = "project_id")])
    @Column(name = "status")
    var statuses: MutableList<String> = mutableListOf(),

    @ElementCollection
    @CollectionTable(name = "project_participants", joinColumns = [JoinColumn(name = "project_id")])
    @Column(name = "participant_id")
    var participants: MutableList<UUID> = mutableListOf()
)