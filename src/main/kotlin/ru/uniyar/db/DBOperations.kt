package ru.uniyar.db

import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException

fun insertUser(user: User) {
    try {
        return transaction {
            Users.insert {
                it[username] = user.userName
                it[password] = user.password
                it[userRole] = user.role
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

fun deleteUserById(id: Int){
    try {
        return transaction {
            Users.deleteWhere {
                Users.userID eq id
            }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}

//!
fun findUserByID(id: Int) : User? {
    try {
        return transaction {
            Users.select {
                (Users.userID eq id)
            }
                .firstOrNull()?.let {
                    User(
                        userID = it[Users.userID],
                        userName = it[Users.username],
                        password = it[Users.password],
                        role = it[Users.userRole],
                    )
                }
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}


fun fetchAllUsers(): List<User> {
    try {
        return transaction {
            Users
                .selectAll()
                .map {
                    row ->
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

fun fetchAllCampaigns() : MutableList<Campaign> {
    try {
        return transaction {
            Campaigns.selectAll().map { row ->
                Campaign(
                    row[Campaigns.campaignID],
                    row[Campaigns.campaignName],
                    row[Campaigns.ownerId],
                )
            }.toMutableList()
        }
    } catch (e: ClassNotFoundException) {
        throw e
    } catch (e: SQLException) {
        throw e
    }
}
