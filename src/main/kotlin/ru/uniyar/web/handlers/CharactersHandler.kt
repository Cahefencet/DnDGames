package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.*
import ru.uniyar.utils.htmlView
import ru.uniyar.utils.userLens
import ru.uniyar.web.models.CharactersPageVM
import ru.uniyar.web.models.NewCharacterPageVM
import ru.uniyar.web.pagination.*

class CharactersHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageAllCharacters || userStruct.role.manageOwnCharacters))
            return Response(Status.FOUND).header("Location", "/")

        val characters =
            if (userStruct.role.manageAllCharacters) {
                fetchAllCharacters()
            } else {
                findCharactersByUserID(userStruct.id)
            }.toMutableList()

        val page = request.query("page")?.toIntOrNull() ?: 1

        val pageAmount = pageAmount(characters, charactersOnPage)

        if (page !in 1 ..pageAmount)
            return Response(Status.FOUND).header("Location", "/Characters")

        val paginator =
            Paginator(
                Uri.of("/Characters"),
                page,
                pageAmount,
            )

        val charactersFilteredByPageNumber =
            filterByPageNumber(characters, charactersOnPage, paginator.getCur())

        val paginationData = getPaginationData(paginator)

        val model =
            CharactersPageVM(
                charactersFilteredByPageNumber,
                chooseFlag = false,
                userStruct,
                paginationData
            )

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class NewCharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageOwnCharacters))
            return Response(Status.FOUND).header("Location", "/")

        val model = NewCharacterPageVM(userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class CharacterCreationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageOwnCharacters))
            return Response(Status.FOUND).header("Location", "/")

        val valid = getValidData(request)

        if (valid.size < 4)
            return Response(Status.FOUND).header("Location", "/Characters")
        
        val name = valid[0]
        val characterClass = valid[1]
        val race = valid[2]
        val level = valid[3].toInt()

        val character =
            Character(
                -1,
                userStruct.id,
                name,
                characterClass,
                race,
                level
            )
        insertCharacter(character)

        val charID = findCharacterIDByNameUserClassRaceLevel(character)

        return Response(Status.FOUND).header("Location", "/Characters/${charID}")
    }

    private fun getValidData(request: Request) : MutableList<String> {

        val notValid = mutableListOf<String>()

        val form = request.form()

        val name = form.findSingle("name") ?: ""
        val characterClass = form.findSingle("characterClass") ?: ""
        val race = form.findSingle("race") ?: ""
        val level = form.findSingle("level") ?: ""

        val valid = mutableListOf<Any>(name, characterClass, race, level)

        if (valid.any { it == "" || it.equals(null) })
            return notValid

        try {
            if (level.toInt() > 20 || level.toInt() < 1)
                return notValid
        } catch (e : NumberFormatException) {
            return notValid
        }

        return mutableListOf(name, characterClass, race, level)
    }
}

class ShowCharactersToChooseHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageOwnCharacters))
            return Response(Status.FOUND).header("Location", "/")

        val campID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1
        val userID = lensOrNull(userIdLens, request)?.toIntOrNull() ?: -1

        findCampaignByID(campID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${campID}/Users")

        if (userStruct.id != userID)
            return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        val characters = findCharactersByUserID(userID).toMutableList()

        val page = request.query("page")?.toIntOrNull() ?: 1

        val pageAmount = pageAmount(characters, charactersOnPage)

        if (page !in 1 ..pageAmount)
            return Response(Status.FOUND).header("Location", "/Choose/${campID}/${userID}")

        val paginator =
            Paginator(
                Uri.of("/Choose/${campID}/${userID}"),
                page,
                pageAmount,
            )

        val charactersFilteredByPageNumber =
            filterByPageNumber(characters, charactersOnPage, paginator.getCur())

        val paginationData = getPaginationData(paginator)

        val model =
            CharactersPageVM(
                charactersFilteredByPageNumber,
                chooseFlag = true,
                userStruct,
                paginationData
            )

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class ChooseCharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageAllCharacters || userStruct.role.manageOwnCharacters))
            return Response(Status.FOUND).header("Location", "/")

        val campID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1
        val userID = lensOrNull(userIdLens, request)?.toIntOrNull() ?: -1

        if (userID != userStruct.id)
            return Response(Status.FOUND).header("Location", "/")

        val charID = request.form().findSingle("charID")?.toIntOrNull() ?: -1

        findCampaignByID(campID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${campID}/Users")

        findCharacterByID(charID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${campID}/Users")

        addCharToCampaign(charID, userID, campID)

        return Response(Status.FOUND).header("Location", "/Campaigns/${campID}/Users")
    }
}
