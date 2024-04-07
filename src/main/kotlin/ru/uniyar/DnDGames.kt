package ru.uniyar

import config.AppConfig.Companion.readConfiguration
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.uniyar.auth.Users
import ru.uniyar.db.DBOperations
import ru.uniyar.utils.DBConnection
import ru.uniyar.utils.getApp

fun main() {
    DBConnection.connect()

    val users = DBOperations.fetchAllUsers()
    users.forEach {
        println(it.userName)
    }

    val server =
        getApp(Users(ArrayList()))
            .asServer(
                Netty(
                    readConfiguration()
                        .webConfig
                        .webPort,
                ),
            ).start()
    println("Server started on http://localhost:${server.port()}")
}
