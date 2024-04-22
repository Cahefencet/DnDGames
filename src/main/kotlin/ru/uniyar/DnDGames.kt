package ru.uniyar

import config.AppConfig.Companion.readConfiguration
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.uniyar.auth.Users
import ru.uniyar.db.fetchAllUsers
import ru.uniyar.utils.DBConnection
import ru.uniyar.utils.getApp

fun main() {
    DBConnection.connect()

    val server =
        getApp(Users(fetchAllUsers()))
            .asServer(
                Netty(
                    readConfiguration()
                        .webConfig
                        .webPort,
                ),
            ).start()

    println("Server started on http://localhost:${server.port()}")
}
