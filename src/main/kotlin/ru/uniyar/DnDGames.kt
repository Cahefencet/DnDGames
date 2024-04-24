package ru.uniyar

import config.AppConfig.Companion.readConfiguration
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.uniyar.utils.DBConnection
import ru.uniyar.utils.getApp

fun main() {
    DBConnection.connect()

    val server =
        getApp()
            .asServer(
                Netty(
                    readConfiguration()
                        .webConfig
                        .webPort,
                ),
            ).start()

    println("Server started on http://localhost:${server.port()}")
}
