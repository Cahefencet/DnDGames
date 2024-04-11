package ru.uniyar

import config.AppConfig.Companion.readConfiguration
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.uniyar.auth.Users
import ru.uniyar.db.fetchAllUsers
import ru.uniyar.db.findUserByID
import ru.uniyar.utils.DBConnection
import ru.uniyar.utils.getApp

fun main() {
    DBConnection.connect()

    val users = findUserByID(3)
    if (users != null)
        println(
            users.userID.toString() + " | " + users.userName + " | " +
            users.password + " | " + users.role.toString()
        )

    val server =
        getApp(Users(ArrayList(fetchAllUsers())))
            .asServer(
                Netty(
                    readConfiguration()
                        .webConfig
                        .webPort,
                ),
            ).start()

    println("Server started on http://localhost:${server.port()}")
}
