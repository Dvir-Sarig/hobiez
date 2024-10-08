package repositories

import com.google.inject.Inject
import com.google.inject.name.Named
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import tables.ClientsTable

class CoachRepository @Inject constructor(
    @Named("coachDatabase") private val coachDatabase: Database
) {
    fun addCoach(name: String, email: String, password: String){
        transaction(coachDatabase) {
            ClientsTable.insert {
                it[ClientsTable.name] = name
                it[ClientsTable.email] = email
                it[ClientsTable.password] = password
            }
        }
    }
}