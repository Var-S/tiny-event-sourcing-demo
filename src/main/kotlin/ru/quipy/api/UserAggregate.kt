package ru.quipy.api

import ru.quipy.core.annotations.AggregateType
import ru.quipy.domain.Aggregate
import java.util.*

@AggregateType(aggregateEventsTableName = "aggregate-user")
class UserAggregate : Aggregate {}
