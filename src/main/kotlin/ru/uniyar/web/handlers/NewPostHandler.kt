package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.findCampaignByID
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.NewPostVM

class NewPostHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val model = NewPostVM(campaign)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}
//
//class PostCreationHandler : HttpHandler {
//    override fun invoke(request: Request): Response {
//
//    }
//}
