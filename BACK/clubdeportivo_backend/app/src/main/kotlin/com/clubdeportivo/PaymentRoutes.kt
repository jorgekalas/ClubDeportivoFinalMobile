package com.clubdeportivo

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*

fun Route.paymentRoutes() {
    val paymentRepo = PaymentRepository()
    val memberRepo  = MemberRepository()

    route("/payments") {
        get {
            call.respond(paymentRepo.getAll())
        }
        post {
            val dto = call.receive<PaymentDTO>()
            val created = paymentRepo.add(dto)

            //  ───> Aquí marcamos al socio como paid = true:
            memberRepo.markPaid(dto.memberId)

            call.respond(created)
        }
    }
}
