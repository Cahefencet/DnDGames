package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.*
import ru.uniyar.utils.htmlView
import ru.uniyar.utils.userLens
import ru.uniyar.web.models.CampaignsPageVM
import ru.uniyar.web.models.NewCampaignPageVM

class CampaignsHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageAllCampaigns || userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val model = CampaignsPageVM(fetchAllCampaigns(), userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class NewCampaignHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val model = NewCampaignPageVM(userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class CampaignCreationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val name = getValidData(request)

        if (name == "not valid")
            return Response(Status.FOUND).header("Location", "/NewCampaign")

        val ownerID = findUserByID(userStruct.id)?.userID ?: -1

        if (fetchAllCampaigns()
            .any { (it.campaignName == name) && (it.ownerID == ownerID) })
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
