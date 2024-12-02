package ru.quipy.projections.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.quipy.api.*
import ru.quipy.projections.tasks.TaskEntity
import ru.quipy.streams.AggregateSubscriptionsManager
import javax.annotation.PostConstruct

@Service
class UsersViewProjection {
    @Autowired
    lateinit var subscriptionsManager: AggregateSubscriptionsManager

    @Autowired
    lateinit var userRepository: UsersProjectionRepository

    @PostConstruct
    fun init() {
        subscriptionsManager.createSubscriber(UserAggregate::class, "users-subscriber") {
            `when`(UserCreatedEvent::class) {
                userRepository.save(UserEntity(
                    id = it.userId,
                    login = it.login,
                ))
            }

            `when`(UserUpdatedEvent::class) {
                val user = userRepository.getReferenceById(it.userId)
                user.login = it.newLogin
                userRepository.save(user)
            }
        }
    }
}