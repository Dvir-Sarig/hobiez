package controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import users.client.Client
import users.client.ClientsManagerService
import users.coach.Coach
import users.coach.CoachesManagerService
import javax.inject.Inject

class CreateUserController @Inject constructor(
    private val clientsManagerService: ClientsManagerService,
    private val coachesManagerService: CoachesManagerService
) {
    fun startRouting(route: Route) = with(route) {
        userCreationRoute()
    }

    private fun Route.userCreationRoute() {
        post("/create-coach") {
            val coach = call.receive<Coach>()
            coachesManagerService.addCoach(coach)
            call.respond(HttpStatusCode.Created, "Coach created successfully")
        }

        post("/create-client") {
            val client = call.receive<Client>()
            clientsManagerService.addClient(client)
            call.respond(HttpStatusCode.Created, "Client created successfully")
        }
    }
}
