package ru.quipy.projections.tasks

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "tasks")
data class TaskEntity(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(name = "project_id", nullable = false)
    val projectId: UUID = UUID.randomUUID(),

    @Column(name = "title", nullable = false)
    val title: String = "",

    @Column(name = "status", nullable = false)
    val status: String = "",

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    val description: String = "",

    @ElementCollection
    @CollectionTable(name = "task_performers", joinColumns = [JoinColumn(name = "task_id")])
    @Column(name = "performer_id")
    val performersIds: List<UUID> = listOf()
)