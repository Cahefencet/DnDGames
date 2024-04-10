package ru.uniyar.web.handlers

import org.http4k.core.*
import ru.uniyar.db.Campaign
import ru.uniyar.db.DBOperations
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CampaignsPageVM

class CampaignsHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val cps = DBOperations.fetchAllCampaigns()
        //val user = DBOperations.findUserByID(3)!!
        cps.add(Campaign(
            1,
            "Example",
            1
        ))
        //1
        val model = CampaignsPageVM(cps)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}