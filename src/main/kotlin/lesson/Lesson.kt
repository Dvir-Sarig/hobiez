package lesson

import users.client.Client
import java.time.Instant

data class Lesson(
    val title: String,
    val description: String,
    val time: Instant,
    val coachId: Int,
    val price: Double,
    val participants: MutableList<Client>,
    val capacityLimit: Int
)
