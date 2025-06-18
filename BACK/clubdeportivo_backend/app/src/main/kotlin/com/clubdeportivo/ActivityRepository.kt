package com.clubdeportivo

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ActivityRepository {
    fun getAll(): List<ActivityDTO> = transaction {
        Activities.selectAll().map {
            ActivityDTO(
                id = it[Activities.id],
                name = it[Activities.name],
                capacity = it[Activities.capacity],
                available = it[Activities.available]
            )
        }
    }

    fun add(activity: ActivityDTO): ActivityDTO = transaction {
        val insertedId = Activities.insert {
            it[name] = activity.name
            it[capacity] = activity.capacity
            it[available] = activity.available
        } get Activities.id

        activity.copy(id = insertedId)
    }
}
