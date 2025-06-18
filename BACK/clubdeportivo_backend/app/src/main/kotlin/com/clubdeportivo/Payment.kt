package com.clubdeportivo

import org.jetbrains.exposed.sql.Table

object Payments : Table() {
    val id = integer("id").autoIncrement()
    val memberId = integer("member_id").references(Members.id)
    val amount = double("amount")
    val date = varchar("date", 20)
    override val primaryKey = PrimaryKey(id)
}

data class PaymentDTO(
    val id: Int? = null,
    val memberId: Int,
    val amount: Double,
    val date: String
)
