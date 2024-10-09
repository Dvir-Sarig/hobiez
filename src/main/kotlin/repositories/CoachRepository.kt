package repositories

import com.google.inject.Inject
import com.google.inject.name.Named
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import tables.CoachesTable
import users.coach.Coach

class CoachRepository @Inject constructor(
    @Named("coachDatabase") private val coachDatabase: Database
) {
    fun addCoach(name: String, email: String, password: String){
        transaction(coachDatabase) {
            CoachesTable.insert {
                it[CoachesTable.name] = name
                it[CoachesTable.email] = email
                it[CoachesTable.password] = password
            }
        }
    }

    fun findCoachByEmail(email: String): Coach? {
        return transaction(coachDatabase) {
            CoachesTable.select { CoachesTable.email eq email }
                .map { rowToCoach(it) }
                .singleOrNull()
        }
    }

    private fun rowToCoach(row: ResultRow): Coach {
        return Coach(
            id = row[CoachesTable.id],
            name = row[CoachesTable.name],
            email = row[CoachesTable.email],
            password = row[CoachesTable.password]
        )
    }
}