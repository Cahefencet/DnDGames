package ru.uniyar.web

import org.http4k.core.Method
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import ru.uniyar.web.handlers.RootHandler

fun router() =
    routes(
        "/" bind Method.GET to RootHandler(),
        static(ResourceLoader.Classpath("ru/uniyar/public")),
    )
