package controllers

import com.google.inject.Inject
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import lesson.LessonsManagerService

class CoachLessonsController @Inject constructor(
    private val lessonsManagerService: LessonsManagerService
) {
    fun startRouting(route: Route) = with(route) {
        get("/coach-lessons/{coachId}") {
            val coachId = call.parameters["coachId"]?.toIntOrNull()
            if (coachId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid coach ID")
                return@get
            }
            val lessons = lessonsManagerService.getLessonsForCoach(coachId)
            call.respond(HttpStatusCode.OK, lessons)
        }
    }
}
