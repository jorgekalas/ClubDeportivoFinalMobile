
package com.clubdeportivo

import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import com.clubdeportivo.ActivityDTO
fun Route.activityRoutes() {
    val repo = ActivityRepository()

    route("/activities") {
        // 1) Listar todas
        get {
            call.respond(repo.getAll())
        }
        // 2) Crear nueva
        post {
            val activity = call.receive<ActivityDTO>()
            call.respond(repo.add(activity))
        }
        // 3) Obtener por ID
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@get
            }
            try {
                call.respond(repo.getById(id))
            } catch (e: NoSuchElementException) {
                call.respond(HttpStatusCode.NotFound, "Actividad $id no existe")
            }
        }
        // 4) Registrar socio en actividad
        post("/{id}/register") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "ID inválido")
                return@post
            }
            val dto = call.receive<RegisterDTO>()
            try {
                repo.registerMember(id, dto.memberId)
                call.respond(HttpStatusCode.OK)
            } catch (ise: IllegalStateException) {
                call.respond(HttpStatusCode.Conflict, mapOf("error" to ise.message))
            } catch (nse: NoSuchElementException) {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to nse.message))
            }
        }
    }
}
