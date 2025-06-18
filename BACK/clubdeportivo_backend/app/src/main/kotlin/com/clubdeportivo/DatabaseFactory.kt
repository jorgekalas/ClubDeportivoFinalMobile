package com.clubdeportivo

import com.clubdeportivo.Members
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import com.clubdeportivo.Payments
import com.clubdeportivo.Activities

object DatabaseFactory {
    fun init() {
        val url = "jdbc:sqlite:club.db"
        Database.connect(url, driver = "org.sqlite.JDBC")
        transaction {
            SchemaUtils.createMissingTablesAndColumns(
                Members,
                Activities,
                Payments
            )
        }
    }
}