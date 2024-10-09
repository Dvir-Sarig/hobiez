package repositories

import com.google.inject.name.Named
import lesson.Lesson
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import tables.ClientLessonsTable
import tables.LessonsTable
import java.time.LocalDateTime
import javax.inject.Inject

class LessonRepository @Inject constructor(
    @Named("lessonsDatabase") private val lessonsDatabase: Database
) {
    fun addLesson(title: String, description: String, time: LocalDateTime, coachId: Int, price: Double, capacityLimit: Int) {
        transaction(lessonsDatabase) {
            LessonsTable.insert {
                it[LessonsTable.title] = title
                it[LessonsTable.description] = description
                it[LessonsTable.time] = time
                it[LessonsTable.coachId] = coachId
                it[LessonsTable.price] = price
                it[LessonsTable.capacityLimit] = capacityLimit
            }
        }
    }

    fun registerClientToLesson(clientId: Int, lessonId: Int) {
        transaction(lessonsDatabase) {
            ClientLessonsTable.insert {
                it[ClientLessonsTable.clientId] = clientId
                it[ClientLessonsTable.lessonId] = lessonId
            }
        }
    }

    fun isClientAlreadyRegistered(clientId: Int, lessonDate: LocalDateTime): Boolean {
        return transaction(lessonsDatabase) {
            val existingLesson = (LessonsTable innerJoin ClientLessonsTable)
                .select { (ClientLessonsTable.clientId eq clientId) and (LessonsTable.time eq lessonDate) }
                .count()
            existingLesson > 0
        }
    }

    fun getLessonById(lessonId: Int): Lesson? {
        return transaction(lessonsDatabase) {
            LessonsTable.select { LessonsTable.id eq lessonId }
                .map { rowToLesson(it) }
                .singleOrNull()
        }
    }

    private fun rowToLesson(row: ResultRow): Lesson {
        return Lesson(
            id = row[LessonsTable.id],
            title = row[LessonsTable.title],
            description = row[LessonsTable.description],
            time = row[LessonsTable.time],
            coachId = row[LessonsTable.coachId],
            price = row[LessonsTable.price],
            capacityLimit = row[LessonsTable.capacityLimit]
        )
    }
}
