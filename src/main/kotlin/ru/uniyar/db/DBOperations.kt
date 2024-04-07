package ru.uniyar.db

import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException

class DBOperations {
    companion object {
        //        fun a() {
//            try {
//                return transaction {
//                    Users.insert {
//                        it[userID] = 1
//                        it[username] = "a"
//                        it[password] = "a"
//                        it[userRole] = Role.ADMIN
//                    }
//                }
//            } catch (e: ClassNotFoundException) {
//                throw e
//            } catch (e: SQLException) {
//                throw e
//            }
//        }

        fun fetchAllUsers(): List<User> {
            try {
                return transaction {
                    Users.selectAll().map { row ->
                        User(
                            row[Users.userID],
                            row[Users.username],
                            row[Users.password],
                            row[Users.userRole],
                        )
                    }
                }
            } catch (e: ClassNotFoundException) {
                throw e
            } catch (e: SQLException) {
                throw e
            }
        }
    }
}
