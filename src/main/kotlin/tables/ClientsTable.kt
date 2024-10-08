package tables

import org.jetbrains.exposed.sql.Table

object ClientsTable : Table("clients") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 255)
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    override val primaryKey = PrimaryKey(id)
}
