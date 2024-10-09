package repositories

import com.google.inject.Inject
import com.google.inject.name.Named
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import tables.CoachesTable

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
}