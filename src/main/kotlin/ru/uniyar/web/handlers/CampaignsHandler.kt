package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.Campaign
import ru.uniyar.db.fetchAllCampaigns
import ru.uniyar.db.findCampIDByOwnerAndName
import ru.uniyar.db.insertCampaign
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CampaignsPageVM
import ru.uniyar.web.models.NewCampaignVM

class CampaignsHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val model = CampaignsPageVM(fetchAllCampaigns())
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class NewCampaignHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val model = NewCampaignVM()
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class CampaignCreationHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val name = getValidData(request)

        if (name == "not valid")
            return Response(Status.FOUND).header("Location", "/NewCampaign")

        //coming soon
        val ownerID = 3

        if (fetchAllCampaigns()
            .any {
                (it.campaignName == name) && (it.ownerID == ownerID)
            })
            return Response(Status.FOUND).header("Location", "/NewCampaign")

        insertCampaign(
            Campaign(
                -1,
                name,
                ownerID
            ))

        val campaignID = findCampIDByOwnerAndName(ownerID, name)
        return Response(Status.FOUND).header("Location", "/Campaigns/${campaignID}")
    }

    private fun getValidData(request: Request) : String {

        val notValid = "not valid"

        val form = request.form()

        val name = form.findSingle("name") ?: ""

        if (name == "" || name.equals(null))
            return notValid

        return name
    }
}
