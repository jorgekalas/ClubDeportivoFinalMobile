package com.clubdeportivo

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.application.*
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.serialization.gson.*
import io.ktor.server.plugins.cors.routing.*    // CORS en Ktor 2.x
import io.ktor.http.*
import com.clubdeportivo.memberRoutes
import com.clubdeportivo.activityRoutes
import com.clubdeportivo.paymentRoutes

fun main() {
    embeddedServer(Netty, port = 8081, host = "0.0.0.0") {
        module()
    }.start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()

    install(ContentNegotiation) {
        gson()
    }

    install(CORS) {
        anyHost()                    // solo en dev
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
    }

    routing {
        get("/") {
            call.respondText("Club Deportivo Backend funcionando.")
        }
        memberRoutes()
        activityRoutes()
        paymentRoutes()
    }
}
