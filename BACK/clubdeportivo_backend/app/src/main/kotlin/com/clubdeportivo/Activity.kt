package com.clubdeportivo

import org.jetbrains.exposed.sql.Table

object Activities : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val capacity = integer("capacity")
    val available = bool("available")
    override val primaryKey = PrimaryKey(id)
}

