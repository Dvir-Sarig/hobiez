package tables

import org.jetbrains.exposed.sql.Table

object ClientLessonsTable : Table("client_lessons") {
    val clientId = integer("client_id").references(ClientsTable.id)
    val lessonId = integer("lesson_id").references(LessonsTable.id)

    override val primaryKey = PrimaryKey(clientId, lessonId)
}
