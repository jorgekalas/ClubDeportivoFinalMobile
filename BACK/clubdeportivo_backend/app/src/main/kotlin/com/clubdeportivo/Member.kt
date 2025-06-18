package com.clubdeportivo

import org.jetbrains.exposed.sql.Table

object Members : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val dni = varchar("dni", 20)
    val email = varchar("email", 100)
    val fitnessApproved = bool("fitness_approved")
    val paid = bool("paid")
    override val primaryKey = PrimaryKey(id)
}

data class MemberDTO(
    val id: Int? = null,
    val name: String,
    val dni: String,
    val email: String,
    val fitnessApproved: Boolean,
    val paid: Boolean
)