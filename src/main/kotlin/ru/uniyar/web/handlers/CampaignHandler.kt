package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Status
import org.http4k.core.Response
import org.http4k.core.with
import org.http4k.core.Request
import ru.uniyar.db.fetchAllPostsOfOneCampaign
import ru.uniyar.db.findCampaignByID
import ru.uniyar.db.findMasterIDByCampID
import ru.uniyar.db.findUserByID
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CampaignPageVM

class CampaignHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val posts = fetchAllPostsOfOneCampaign(campaignID)

        val userID = findMasterIDByCampID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val master = findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val model = CampaignPageVM(campaign, master, posts)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}
