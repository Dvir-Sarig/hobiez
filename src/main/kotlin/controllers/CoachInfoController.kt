package controllers

import com.google.inject.Inject
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import lesson.LessonsManagerService
import users.coach.CoachesManagerService

class CoachInfoController @Inject constructor(
    private val coachesManagerService: CoachesManagerService,
    private val lessonsManagerService: LessonsManagerService
) {
    fun startRouting(route: Route) = with(route){
        getCoachInfoRoutes()
    }

    private fun Route.getCoachInfoRoutes(){
        get("/coach-lessons/{coachId}") {
            val coachId = call.parameters["coachId"]?.toIntOrNull()
            if (coachId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid coach ID")
                return@get
            }
            val lessons = lessonsManagerService.getLessonsForCoach(coachId)
            call.respond(HttpStatusCode.OK, lessons)
        }

        get("/coach-info/{coachId}"){
            val coachId = call.parameters["coachId"]?.toIntOrNull()
            if (coachId == null) {
                call.respond(HttpStatusCode.BadRequest, "Invalid client ID")
                return@get
            }
            val coach = coachesManagerService.findCoachById(coachId)
            call.respond(HttpStatusCode.OK, coach)
        }
    }
}