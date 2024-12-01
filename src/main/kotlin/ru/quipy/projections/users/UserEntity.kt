package ru.quipy.projections.users

import java.util.*
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class UserEntity(
    @Id
    val id: UUID = UUID.randomUUID(),
    val login: String = "",
)