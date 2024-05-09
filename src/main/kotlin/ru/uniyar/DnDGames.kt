package ru.uniyar

import config.ServerConfig.Companion.readConfiguration
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.uniyar.auth.Hasher
import ru.uniyar.utils.DBConnection
import ru.uniyar.utils.getApp

fun main() {
    val configuration = readConfiguration()

    DBConnection.connect(configuration)

    val server =
        getApp()
            .asServer(
                Netty(
                    configuration.
                    webPort,
                ),
            ).start()

    println("Server started on http://localhost:${server.port()}")
}
