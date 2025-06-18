package com.clubdeportivo

import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*

fun Route.activityRoutes() {
    val repo = ActivityRepository()

    route("/activities") {
        get {
            call.respond(repo.getAll())
        }
        post {
            val activity = call.receive<ActivityDTO>()
            call.respond(repo.add(activity))
        }
    }
}
