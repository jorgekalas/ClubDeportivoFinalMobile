package com.clubdeportivo

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class MemberRepository {
    fun getAll(): List<MemberDTO> = transaction {
        Members.selectAll().map {
            MemberDTO(
                id = it[Members.id],
                name = it[Members.name],
                dni = it[Members.dni],
                email = it[Members.email],
                fitnessApproved = it[Members.fitnessApproved],
                paid = it[Members.paid]
            )
        }
    }

    fun findByDni(dni: String): MemberDTO? = transaction {
        Members.select { Members.dni eq dni }
            .map { row ->
                MemberDTO(
                    id = row[Members.id],
                    name = row[Members.name],
                    dni = row[Members.dni],
                    email = row[Members.email],
                    fitnessApproved = row[Members.fitnessApproved],
                    paid = row[Members.paid]
                )
            }
            .singleOrNull()
    }

    fun add(member: MemberDTO): MemberDTO = transaction {
        val insertedId = Members.insert {
            it[name] = member.name
            it[dni] = member.dni
            it[email] = member.email
            it[fitnessApproved] = member.fitnessApproved
            it[paid] = member.paid
        } get Members.id

        member.copy(id = insertedId)
    }
    fun markPaid(memberId: Int) = transaction {
        Members.update({ Members.id eq memberId }) {
            it[paid] = true
        }
    }
}
