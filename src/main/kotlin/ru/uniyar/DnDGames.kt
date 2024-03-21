package ru.uniyar

import org.http4k.core.Method.GET
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import org.http4k.server.Netty
import org.http4k.server.asServer
import org.http4k.template.PebbleTemplates
import org.http4k.template.TemplateRenderer
import ru.uniyar.web.handlers.RootHandler

fun router(renderer: TemplateRenderer) =
    routes(
        "/" bind GET to RootHandler(renderer),
        static(ResourceLoader.Classpath("ru/uniyar/public")),
    )

fun main() {
    val pt = PebbleTemplates().HotReload("src/main/resources/")
    val app = router(pt)
    val server = app.asServer(Netty(9000)).start()

    println("Server started on http://localhost:${server.port()}")
}
