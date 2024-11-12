package ru.quipy.projections

import org.springframework.stereotype.Service
import ru.quipy.Models.UserViewDomain
import ru.quipy.Repository.UserRepository
import ru.quipy.api.UserAggregate
import ru.quipy.api.UserCreatedEvent
import ru.quipy.streams.annotation.AggregateSubscriber
import ru.quipy.streams.annotation.SubscribeEvent
import java.util.*

@Service
@AggregateSubscriber(aggregateClass = UserAggregate::class, subscriberName = "user-data-sub")
class UserViewService(
    private val userRepository: UserRepository
) {

    @SubscribeEvent
    fun saveUser(event: UserCreatedEvent) {
        userRepository.save(
            UserViewDomain.User(event.id, event.login)
        )
    }

    fun changeLogin(id: UUID, newLogin: String): UserViewDomain.User? {
        val user = userRepository.findById(id).orElse(null)
        return if (user != null) {
            user.login = newLogin
            userRepository.save(user)
        } else {
            null
        }
    }

    fun findById(userId: UUID): UserViewDomain.User? {
        return userRepository.findById(userId).orElse(null)
    }

    fun findByLogin(login: String): UserViewDomain.User? {
        return userRepository.findByLogin(login)
    }
}
