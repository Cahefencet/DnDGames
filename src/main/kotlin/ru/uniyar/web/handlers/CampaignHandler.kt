package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Status
import org.http4k.core.Response
import org.http4k.core.with
import org.http4k.core.Request
import ru.uniyar.db.findCampaignByID
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CampaignPageVM

class CampaignHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/")

        val model = CampaignPageVM(campaign)

        return Response(Status.OK).with(htmlView(request) of model)

    }
}