package ru.quipy.Repository

import org.springframework.data.mongodb.repository.MongoRepository
import ru.quipy.Models.UserViewDomain
import java.util.*

interface UserRepository : MongoRepository<UserViewDomain.User, UUID> {
    fun findByLogin(login: String): UserViewDomain.User?
    override fun findById(id: UUID): Optional<UserViewDomain.User>
}
