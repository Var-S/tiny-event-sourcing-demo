package ru.quipy.projections

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.projections.users.UserEntity
import ru.quipy.projections.users.UsersProjectionRepository
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent

@Service
@AggregateSubscriber(
    aggregateClass = UserAggregate::class, subscriberName = "demo-users-subs-stream"
)
class AnnotationBasedUserEventsSubscriber(
    val usersRepository: UsersProjectionRepository
) {
    @SubscribeEvent
    fun userCreatedEventSubscriber(event: UserCreatedEvent) {
        usersRepository.save(UserEntity(event.userId, event.login))
    }
}