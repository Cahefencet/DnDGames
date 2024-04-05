package ru.uniyar

import config.AppConfig.Companion.readConfiguration
import org.http4k.core.Method.GET
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Netty
import org.http4k.server.asServer
import ru.uniyar.auth.Users
import ru.uniyar.db.DBOperations
import ru.uniyar.utils.DBConnection
import ru.uniyar.utils.getApp
import ru.uniyar.web.handlers.RootHandler

fun router() =
    routes(
        "/" bind GET to RootHandler(),
        static(ResourceLoader.Classpath("ru/uniyar/public")),
    )

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
