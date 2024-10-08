package repositories

import com.google.inject.name.Named
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction
import tables.ClientsTable
import javax.inject.Inject

class ClientRepository @Inject constructor(
    @Named("clientDatabase") private val clientDatabase: Database
) {

    fun addClient(name: String, email: String, password: String){
        transaction(clientDatabase) {
            ClientsTable.insert {
                it[ClientsTable.name] = name
                it[ClientsTable.email] = email
                it[ClientsTable.password] = password
            }
        }
    }

    fun updateClient(id: Int, name: String, email: String, password: String): Boolean {
        return transaction(clientDatabase) {
            ClientsTable.update({ ClientsTable.id eq id }) {
                it[ClientsTable.name] = name
                it[ClientsTable.email] = email
                it[ClientsTable.password] = password
            } > 0
        }
    }

    fun deleteClient(id: Int): Boolean {
        return transaction(clientDatabase) {
            ClientsTable.deleteWhere { ClientsTable.id eq id } > 0
        }
    }
}
