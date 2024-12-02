package ru.quipy.projections.users

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "users")
data class UserEntity(
    @Id
    @Column(name = "id", nullable = false, updatable = false)
    var id: UUID = UUID.randomUUID(),

    @Column(name = "login", nullable = false)
    var login: String = "",
)