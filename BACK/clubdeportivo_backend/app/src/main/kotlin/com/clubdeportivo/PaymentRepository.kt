package com.clubdeportivo

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class PaymentRepository {
    fun getAll(): List<PaymentDTO> = transaction {
        Payments.selectAll().map {
            PaymentDTO(
                id = it[Payments.id],
                memberId = it[Payments.memberId],
                amount = it[Payments.amount],
                date = it[Payments.date]
            )
        }
    }

    fun add(payment: PaymentDTO): PaymentDTO = transaction {
        val insertedId = Payments.insert {
            it[memberId] = payment.memberId
            it[amount] = payment.amount
            it[date] = payment.date
        } get Payments.id

        payment.copy(id = insertedId)
    }
}
