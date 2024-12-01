package ru.quipy.projections.users

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

@Repository
interface UsersProjectionRepository : JpaRepository<UserEntity, UUID>