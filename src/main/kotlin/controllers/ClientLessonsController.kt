package controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import lesson.LessonsManagerService
import javax.inject.Inject

class ClientLessonsController @Inject constructor(
    private val lessonsManagerService: LessonsManagerService
) {
    fun startRouting(route: Route) = with(route) {
        get("/client-lessons/{clientId}") {
            val clientId = call.parameters["clientId"]?.toIntOrNull()
            if (clientId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client ID")
                return@get
            }
            val userId = call.principal<JWTPrincipal>()?.payload?.subject?.toInt()
            if (userId != clientId) {
                call.respond(HttpStatusCode.Forbidden, "You do not have permission to view this client's lessons")
                return@get
            }
            val lessons = lessonsManagerService.getLessonsForClient(clientId)
            call.respond(HttpStatusCode.OK, lessons)
        }
    }
}

