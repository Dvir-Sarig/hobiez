package users.coach

import com.google.inject.Inject
import repositories.CoachRepository

class CoachesManagerService @Inject constructor(
    private val coachRepository: CoachRepository
) {
    fun addCoach(coach: Coach) {
        coachRepository.addCoach(coach.name, coach.email, coach.password)
    }
}