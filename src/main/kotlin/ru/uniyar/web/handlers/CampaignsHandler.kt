package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Status
import org.http4k.core.Response
import org.http4k.core.with
import org.http4k.core.Request
import ru.uniyar.db.Campaign
import ru.uniyar.db.fetchAllCampaigns
import ru.uniyar.db.findUserByID
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CampaignsPageVM

class CampaignsHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val cps = fetchAllCampaigns()
        val user = findUserByID(3)!!
        cps.add(Campaign(
            1,
            "Example",
            user.userID
        ))

        val model = CampaignsPageVM(cps)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}