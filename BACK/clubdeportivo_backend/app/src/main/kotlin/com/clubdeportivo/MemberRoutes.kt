package com.clubdeportivo

import com.clubdeportivo.MemberDTO
import com.clubdeportivo.MemberRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.memberRoutes() {
    val repo = MemberRepository()

    route("/members") {
        get {
            call.respond(repo.getAll())
        }
        post {
            val member = call.receive<MemberDTO>()
            call.respond(repo.add(member))
        }
        get("{id}") {
            val idParam = call.parameters["id"] ?: return@get call.respondText(
                "Missing or malformed id", status = HttpStatusCode.BadRequest
            )
            val id = idParam.toIntOrNull() ?: return@get call.respondText(
                "Id must be a number", status = HttpStatusCode.BadRequest
            )
            // obtenemos uno solo
            val member = repo.getAll().find { it.id == id }
            if (member == null) {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(member)
            }
    }}
        get("dni/{dni}") {
            val dni = call.parameters["dni"]!!
            val member = MemberRepository().findByDni(dni)
            if (member == null) {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            } else {
                call.respond(member)
            }
        }
    }
