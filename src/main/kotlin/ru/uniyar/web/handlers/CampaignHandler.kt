package ru.uniyar.web.handlers

import jdk.jfr.DataAmount
import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.*
import ru.uniyar.utils.htmlView
import ru.uniyar.utils.userLens
import ru.uniyar.web.models.*
import ru.uniyar.web.pagination.Paginator
import ru.uniyar.web.pagination.PostPaginationData
import java.time.LocalDate

class CampaignHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        // user - own, redactor - all
        if (!(userStruct.role.manageAllCampaigns || userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val user = findUserByID(userStruct.id)
            ?: return Response(Status.FOUND).header("Location", "/")

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        if (!(isUserInCampaign(userStruct.id, campaignID)))
            if (!(userStruct.role.manageAllCampaigns))
                return Response(Status.FOUND).header("Location","/Campaigns")

        val masterID = findMasterIDByCampID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val master = findUserByID(masterID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val dates = fetchAllPostDatesOfOneCampaign(campaignID, user) // size == 3 для игрока

        var pageAmount = dates.size
        if (pageAmount == 0)
            pageAmount = 1

        val page = request.query("page")?.toIntOrNull() ?: pageAmount

        if (page !in 1..pageAmount)
            return Response(Status.FOUND).header("Location", "/Campaigns/${campaignID}")

        val paginationData = getPaginationData(dates, campaignID)

        val paginationFlag = (paginationData.size > 0)

        val posts : MutableList<CampaignPost> =
            if (dates.size == 0){
                mutableListOf()
            }
            else
                fetchAllPostsOfOneCampaignByGameDateAndPlayer(campaignID, dates[page-1], user).toMutableList()

        val curDateNadNumber = getDateAndNumber(page, dates)

        val model = CampaignPageVM(campaign, master, posts, userStruct, paginationData, paginationFlag, curDateNadNumber)

        return Response(Status.OK).with(htmlView(request) of model)
    }

    private fun getDateAndNumber(page: Int, dates: MutableList<LocalDate>) : CurDateAndNumber {
        val date = dates[page-1]
        return CurDateAndNumber(date, page)
    }

    private fun getPaginationData(dates : MutableList<LocalDate>, campaignID: Int) : MutableList<PostPaginationData> {
        val data = mutableListOf<PostPaginationData>()
        dates.forEach {
            data.add(PostPaginationData(
                it,
                Uri.of("/Campaigns/${campaignID}?page=${dates.indexOf(it) + 1}")
            ))
        }
        return data
    }
}

class CurDateAndNumber(val date: LocalDate, val num : Int)

class CampaignUsersHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        // user - own, redactor - all
        if (!(userStruct.role.manageAllCampaigns || userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        if (!(isUserInCampaign(userStruct.id, campaignID)))
            if (!(userStruct.role.manageAllCampaigns))
                return Response(Status.FOUND).header("Location","/Campaigns")

        val players = getData(campaignID)

        val flag = (players.size == 0)

        val model = CampaignUsersPageVM(campaign, players, flag, userStruct)

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
        val userStruct = userLens(request) ?: return Response(Status.FOUND).header("Location", "/")

        // user - own, redactor - all
        if (!(userStruct.role.manageAllCampaigns || userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        if (userStruct.id != campaign.ownerID)
            if (!(userStruct.role.manageAllCampaigns))
                return Response(Status.FOUND).header("Location", "/Campaigns/${campaignID}")

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
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        // user - own, redactor - all
        if (!(userStruct.role.manageAllCampaigns || userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val userID = lensOrNull(userIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns/${campaignID}")

        val user = findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns/${campaignID}")

        if ((userStruct.id != campaign.ownerID) && (userStruct.id != userID))
            if (!(userStruct.role.manageAllCampaigns))
                return Response(Status.FOUND).header("Location","/Campaigns/${campaignID}")

        val model = KickUserFromCampaignConfirmationPageVM(user, campaign, userStruct)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class KickUserFromCampaignHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request) ?: return Response(Status.FOUND).header("Location", "/")

        // user - own, redactor - all
        if (!(userStruct.role.manageAllCampaigns || userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val valid = getValidData(request)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        val masterID = findMasterIDByCampID(valid.campaignID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${valid.campaignID}")

        if ((userStruct.id != masterID) && (userStruct.id != valid.userID))
            if (!(userStruct.role.manageAllCampaigns))
                return Response(Status.FOUND).header("Location", "/Campaigns/${valid.campaignID}")

        deleteFromCampaignUsersByUserIDCampaignID(valid.userID, valid.campaignID)

        return Response(Status.FOUND).header("Location", "/Campaigns/${valid.campaignID}/Users")
    }

    private fun getValidData(request: Request) : UserIDCampaignID? {
        val form = request.form()

        val requestUserID = lensOrNull(userIdLens, request)?.toIntOrNull() ?: -1
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
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        // user - own, redactor - all
        if (!(userStruct.role.manageAllCampaigns || userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val campID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1

        val campaign = findCampaignByID(campID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        if (userStruct.id != campaign.ownerID)
            if (!(userStruct.role.manageAllCampaigns))
                return Response(Status.FOUND).header("Location","/Campaigns/${campID}")

        val model = DeleteCampaignConfirmationPageVM(campaign, userStruct)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteCampaignHandler: HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        // user - own, redactor - all
        if (!(userStruct.role.manageAllCampaigns || userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val requestedCampID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1

        val formCampID = request.form().findSingle("campId")?.toIntOrNull() ?: -2

        if (requestedCampID != formCampID)
            return Response(Status.FOUND).header("Location", "/Campaigns")

        val campaign = findCampaignByID(formCampID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        if (campaign.ownerID == userStruct.id || userStruct.role.manageAllCampaigns)
            deleteCampaignByID(formCampID)

        return Response(Status.FOUND).header("Location", "/Campaigns")
    }
}

class EditCampaignConfirmationHandler: HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val campID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1

        val campaign = findCampaignByID(campID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        if (userStruct.id != campaign.ownerID)
            if (!(userStruct.role.manageAllCampaigns))
                return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        val model = EditCampaignNamePageVM(campaign, userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class EditCampaignHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val requestedCampId = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1

        val form = request.form()

        val formCampId = form.findSingle("campId")?.toIntOrNull() ?: -2

        if (requestedCampId != formCampId)
            return Response(Status.FOUND).header("Location", "/Campaigns")

        val newName = form.findSingle("newName") ?: ""

        val campaign = findCampaignByID(formCampId)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        if (campaign.campaignName == newName
            || newName.isEmpty()
            || newName.length > 100)
            return Response(Status.FOUND).header("Location", "/Campaigns/${requestedCampId}")

        if (userStruct.id != campaign.ownerID)
            if (!(userStruct.role.manageAllCampaigns))
                return Response(Status.FOUND).header("Location", "/Campaigns/${requestedCampId}")

        editCampaignName(requestedCampId, newName)

        return Response(Status.FOUND).header("Location", "/Campaigns/${requestedCampId}")
    }
}
