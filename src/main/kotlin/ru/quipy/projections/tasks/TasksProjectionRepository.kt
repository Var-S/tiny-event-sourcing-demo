package ru.quipy.projections.tasks

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface TasksProjectionRepository : JpaRepository<TaskEntity, UUID>