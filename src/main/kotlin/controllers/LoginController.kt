package controllers

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import services.TokenService
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
    private val tokenService: TokenService
){

    fun startRouting(route: Route) = with(route) {
        loginRoute()
    }

    private fun Route.loginRoute() {
        post("/login") {
            val loginRequest = call.receive<LoginRequest>()
            val user = userManagerService.authenticateUser(loginRequest)
            if (user != null) {
                val token = tokenService.generateToken(user)
                call.respond(HttpStatusCode.OK, mapOf("token" to token))
            } else {
                call.respond(HttpStatusCode.Unauthorized, "Invalid email, password, or user type")
            }
        }
    }

}