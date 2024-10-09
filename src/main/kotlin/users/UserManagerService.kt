package users

import controllers.LoginRequest
import services.TokenService
import users.client.ClientsManagerService
import users.coach.CoachesManagerService
import javax.inject.Inject

data class LoginResponse(val token: String, val userId: Int)
class UserManagerService @Inject constructor(
    private val clientsManagerService: ClientsManagerService,
    private val coachesManagerService: CoachesManagerService,
    private val tokenService: TokenService
) {
    fun authenticateUser(loginRequest: LoginRequest): LoginResponse? {
        val user = when (loginRequest.userType) {
            UserType.CLIENT -> {
                val client = clientsManagerService.findClientByEmail(loginRequest.email)
                if (client != null && client.password == loginRequest.password) client else null
            }
            UserType.COACH -> {
                val coach = coachesManagerService.findCoachByEmail(loginRequest.email)
                if (coach != null && coach.password == loginRequest.password) coach else null
            }
        }

        return user?.let {
            val token = tokenService.generateToken(it)
            LoginResponse(token, it.id!!)
        }
    }
}
