package ru.quipy.projections.tasks

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tasks")
data class TaskEntity(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "project_id", nullable = false)
    var projectId: UUID = UUID.randomUUID(),

    @Column(name = "title", nullable = false)
    var title: String = "",

    @Column(name = "status", nullable = false)
    var status: String = "",

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    var description: String = "",

    @ElementCollection
    @CollectionTable(name = "task_performers", joinColumns = [JoinColumn(name = "task_id")])
    @Column(name = "performer_id")
    var performersIds: MutableList<UUID> = mutableListOf()
)