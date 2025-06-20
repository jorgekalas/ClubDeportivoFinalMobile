package com.clubdeportivo

import com.clubdeportivo.Members
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.clubdeportivo.Payments
import com.clubdeportivo.Activities
import com.clubdeportivo.Participants
import org.jetbrains.exposed.sql.selectAll        // <— para selectAll()
import org.jetbrains.exposed.sql.insert

object DatabaseFactory {
    fun init() {
        val url = "jdbc:sqlite:club.db"
        Database.connect(url, driver = "org.sqlite.JDBC")
        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                Members,
                Activities,
                Participants,
                Payments
            )


            // Si no hay ninguna actividad, creamos algunas de ejemplo:
            if (Activities.selectAll().empty()) {
                val ejemplos = listOf(
                    ActivityDTO(id=1, name="CrossFit",    capacity=5,  available=true),
                    ActivityDTO(id=2, name="Futbol 7",    capacity=0,  available=false),
                    ActivityDTO(id=3, name="Funcional",   capacity=2,  available=true),
                    ActivityDTO(id=4, name="Musculación", capacity=2,  available=true),
                    ActivityDTO(id=5, name="Natación",    capacity=1,  available=true)
                )
                ejemplos.forEach { dto ->
                    Activities.insert { row ->

                        row[name]      = dto.name
                        row[capacity]  = dto.capacity
                        row[available] = dto.available
                    }
                }
            }
        }
    }
}