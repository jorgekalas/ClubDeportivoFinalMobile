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

    fun getById(id: Int): ActivityDTO {
        val row = Activities.select { Activities.id eq id }.singleOrNull()
            ?: throw NoSuchElementException("Actividad $id no existe")
        return ActivityDTO(
            id        = row[Activities.id],
            name      = row[Activities.name],
            capacity  = row[Activities.capacity],
            available = row[Activities.available]
        )
    }

    fun add(activity: ActivityDTO): ActivityDTO = transaction {
        val insertedId = Activities.insert {
            it[name] = activity.name
            it[capacity] = activity.capacity
            it[available] = activity.available
        } get Activities.id

        activity.copy(id = insertedId)
    }

    fun registerMember(activityId: Int, memberId: Int) = transaction {
        // 1) Consultar cupo actual
        val actividad = Activities
            .select { Activities.id eq activityId }
            .firstOrNull()
            ?: throw NoSuchElementException("Actividad no existe")

        val cupo = actividad[Activities.capacity]
        if (cupo <= 0) throw IllegalStateException("Sin cupo")

        // 2) Insertar en la tabla de inscripciones
        Participants.insert {
            it[this.activityId] = activityId
            it[this.memberId]   = memberId
        }

        // 3) Reducir capacity y si llega a 0, marcar unavailable
        Activities.update({ Activities.id eq activityId }) {
            it[capacity]  = cupo - 1
            it[available] = (cupo - 1) > 0
        }
    }
    }

