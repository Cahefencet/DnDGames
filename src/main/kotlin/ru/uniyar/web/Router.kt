package ru.uniyar.web

import org.http4k.core.Method.GET
//import org.http4k.core.Method.POST
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import ru.uniyar.web.handlers.CampaignsHandler
import ru.uniyar.web.handlers.RootHandler

fun router() =
    routes(
        "/" bind GET to RootHandler(),
        "/Campaigns" bind GET to CampaignsHandler(),
        static(ResourceLoader.Classpath("ru/uniyar/public")),
    )
