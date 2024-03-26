package ru.uniyar.db

import org.jetbrains.exposed.sql.Table
import ru.uniyar.auth.Role

object Users : Table() {
    val userID = integer("user_id")
    val username = varchar("username", 50)
    val password = varchar("hashed_password", 255)
    val userRole = enumeration<Role>("user_role")
}
