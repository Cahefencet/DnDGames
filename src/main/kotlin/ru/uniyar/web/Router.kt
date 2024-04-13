package ru.uniyar.web

import org.http4k.core.Method.GET
import org.http4k.routing.ResourceLoader
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.routing.static
import ru.uniyar.web.handlers.*

fun router() =
    routes(
        "/" bind GET to RootHandler(),
        "/Campaigns" bind GET to CampaignsHandler(),
        "/Campaigns/{campaignID}" bind GET to CampaignHandler(),
        "/Users" bind GET to UsersHandler(),
        "/Users/{userID}" bind GET to UserHandler(),
        "/Characters" bind GET to CharactersHandler(),
        "/Characters/{characterID}" bind GET to CharacterHandler(),
        static(ResourceLoader.Classpath("ru/uniyar/public")),
    )
