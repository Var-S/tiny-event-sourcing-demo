package ru.quipy.projections.projects

import java.util.*
import javax.persistence.*

@Entity
data class ProjectEntity(
    @Id
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    val title: String = "",

    @ElementCollection
    @CollectionTable(name = "project_statuses", joinColumns = [JoinColumn(name = "project_id")])
    @Column(name = "status")
    val statuses: List<String> = emptyList(),

    @ElementCollection
    @CollectionTable(name = "project_participants", joinColumns = [JoinColumn(name = "project_id")])
    @Column(name = "participant_id")
    val participantIds: List<UUID> = emptyList()
)