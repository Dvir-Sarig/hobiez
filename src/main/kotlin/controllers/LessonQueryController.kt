package controllers

import com.google.inject.Inject
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import lesson.LessonsManagerService
import lesson.requests.LessonSearchRequest

class LessonQueryController @Inject constructor(
    private val lessonsManagerService: LessonsManagerService
) {
    fun startRouting(route: Route) = with(route) {
        availableLessonsRoute()
    }

    private fun Route.availableLessonsRoute() {
        get("/available-lessons") {
            val availableLessons = lessonsManagerService.getAvailableLessons()
            call.respond(HttpStatusCode.OK, availableLessons)
        }

        post("/search-lessons") {
            val searchRequest = call.receive<LessonSearchRequest>()
            val filteredLessons = lessonsManagerService.getFilteredLessons(searchRequest)
            call.respond(HttpStatusCode.OK, filteredLessons)
        }

        get("/sorted/asc") {
            val sortedLessons = lessonsManagerService.getAvailableLessonsSortedByPriceAsc()
            call.respond(HttpStatusCode.OK, sortedLessons)
        }

        get("/sorted/desc") {
            val sortedLessons = lessonsManagerService.getAvailableLessonsSortedByPriceDesc()
            call.respond(HttpStatusCode.OK, sortedLessons)
        }
    }
}
