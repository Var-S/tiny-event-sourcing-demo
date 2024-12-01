package ru.quipy.projections.projects

import org.springframework.stereotype.Repository
import org.springframework.data.jpa.repository.JpaRepository
import ru.quipy.projections.users.UserEntity
import java.util.*

@Repository
interface ProjectsProjectionRepository : JpaRepository<ProjectEntity, UUID>