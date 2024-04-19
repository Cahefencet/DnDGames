package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.*
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CampaignPageVM
import ru.uniyar.web.models.CampaignUsersPageVM
import ru.uniyar.web.models.DeleteCampaignConfirmationVM
import ru.uniyar.web.models.KickUserFromCampaignConfirmationVM
import java.util.HashMap

class CampaignHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val posts = fetchAllPostsOfOneCampaign(campaignID).reversed()

        val userID = findMasterIDByCampID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val master = findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val model = CampaignPageVM(campaign, master, posts)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class CampaignUsersHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val players = getData(campaignID)

        val flag = (players.size == 0)

        val model = CampaignUsersPageVM(campaign, players, flag)

        return Response(Status.OK).with(htmlView(request) of model)
    }

    private fun getData(campaignID: Int) : MutableList<UserCharacter> {
        val players = fetchAllPlayersByCampaignID(campaignID)

        val userCharacters = mutableListOf<UserCharacter>()

        players.forEach {
            if (it.characterID != null)
                userCharacters.add(UserCharacter(findUserByID(it.userID)!!, it, findCharacterByID(it.characterID)))
            else
                userCharacters.add(UserCharacter(findUserByID(it.userID)!!, it, null))
        }

        return userCharacters
    }
}

class AddPlayerHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        val form = request.form()

        val nameOrId = form.findSingle("input")
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${campaignID}/Users")

        val user = findUserByID(nameOrId.toIntOrNull() ?: -1)
            ?: findUserByName(nameOrId)

        if (user == null)
            return Response(Status.FOUND).header("Location", "/Campaigns/${campaignID}/Users")

        insertPlayer(user.userID, campaignID)
        return Response(Status.FOUND).header("Location", "/Campaigns/${campaignID}/Users")
    }
}

class KickUserFromCampaignConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val userID = lensOrNull(userIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns/${campaignID}")

        val user = findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns/${campaignID}")

        val model = KickUserFromCampaignConfirmationVM(user, campaign)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class KickUserFromCampaignHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val valid = getValidData(request)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        deleteFromCampaignUsersByUserIDCampaignID(valid.userID, valid.campaignID)

        return Response(Status.FOUND).header("Location", "/Campaigns/${valid.campaignID}/Users")
    }

    private fun getValidData(request: Request) : UserIDCampaignID? {
        val form = request.form()

        val requestUserID = lensOrNull(characterIdLens, request)?.toIntOrNull() ?: -1
        val requestCampID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1

        val formUserID = form.findSingle("userID")?.toIntOrNull() ?: -2
        val formCampID = form.findSingle("campaignID")?.toIntOrNull() ?: -2

        if (requestUserID != formUserID || requestCampID != formCampID)
            return null

        val campaign = findCampaignByID(requestCampID)
            ?: return null

        val user = findUserByID(requestUserID)
            ?: return null

        return UserIDCampaignID(user.userID, campaign.campaignID)
    }

    private data class UserIDCampaignID(val userID: Int, val campaignID: Int)
}

class DeleteCampaignConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val campID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1

        val campaign = findCampaignByID(campID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        val model = DeleteCampaignConfirmationVM(campaign)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteCampaignHandler: HttpHandler {
    override fun invoke(request: Request): Response {
        val requestedCampID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1

        val formCampID = request.form().findSingle("campId")?.toIntOrNull() ?: -2

        if (requestedCampID != formCampID)
            return Response(Status.FOUND).header("Location", "/Campaigns")

        val campaign = findCampaignByID(formCampID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        // coming soon
        val userID = 3

        if (campaign.ownerID == userID)
            deleteCampaignByID(formCampID)

        return Response(Status.FOUND).header("Location", "/Campaigns")
    }
}

