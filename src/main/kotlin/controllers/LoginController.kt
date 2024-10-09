package controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import users.UserManagerService
import users.UserType
import javax.inject.Inject

data class LoginRequest(
    val email: String,
    val password: String,
    val userType: UserType
)

class LoginController @Inject constructor(
    private val userManagerService: UserManagerService,
){

    fun startRouting(route: Route) = with(route) {
        loginRoute()
    }

    private fun Route.loginRoute() {
        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val loginResponse = userManagerService.authenticateUser(loginRequest)
            if (loginResponse != null) {
                call.respond(HttpStatusCode.OK, loginResponse)
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid email, password, or user type")
            }
        }
    }

}