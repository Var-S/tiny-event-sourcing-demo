package ru.quipy.projections.tasks

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Service
import ru.quipy.api.ProjectAggregate
import ru.quipy.streams.annotation.AggregateSubscriber
import java.util.*

@Service
@AggregateSubscriber(
    aggregateClass = ProjectAggregate::class, subscriberName = "demo-subs-stream"
)
@Repository
interface TasksProjectionRepository : JpaRepository<TaskEntity, UUID>