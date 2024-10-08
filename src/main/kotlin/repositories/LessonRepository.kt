package repositories

import com.google.inject.name.Named
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import tables.LessonsTable
import java.time.Instant
import java.time.ZoneOffset
import javax.inject.Inject

class LessonRepository @Inject constructor(
    @Named("lessonsDatabase") private val lessonsDatabase: Database
) {
    fun addLesson(title: String, description: String, time: Instant, coachId: Int, price: Double, capacityLimit: Int) {
        transaction(lessonsDatabase) {
            LessonsTable.insert {
                it[LessonsTable.title] = title
                it[LessonsTable.description] = description
                it[LessonsTable.time] = time.atZone(ZoneOffset.UTC).toLocalDateTime()
                it[LessonsTable.coachId] = coachId
                it[LessonsTable.price] = price
                it[LessonsTable.capacityLimit] = capacityLimit
            }
        }
    }
}
