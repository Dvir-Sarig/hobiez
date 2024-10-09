package users

import controllers.LoginRequest
import users.client.ClientsManagerService
import users.coach.CoachesManagerService
import javax.inject.Inject

class UserManagerService @Inject constructor(
    private val clientsManagerService: ClientsManagerService,
    private val coachesManagerService: CoachesManagerService
) {
    fun authenticateUser(loginRequest: LoginRequest): User? {
        return when (loginRequest.userType) {
            UserType.CLIENT -> {
                val client = clientsManagerService.findClientByEmail(loginRequest.email)
                if (client != null && client.password == loginRequest.password) client else null
            }
            UserType.COACH -> {
                val coach = coachesManagerService.findCoachByEmail(loginRequest.email)
                if (coach != null && coach.password == loginRequest.password) coach else null
            }
        }
    }
}
