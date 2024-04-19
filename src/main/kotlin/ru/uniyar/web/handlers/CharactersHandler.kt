package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.auth.Role
import ru.uniyar.db.*
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CharactersPageVM
import ru.uniyar.web.models.NewCharacterPageVM

class CharactersHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val user = User(
            3,
            "example",
            "example",
            Role.MODERATOR
        )

        if (user.role == Role.MODERATOR)
            return Response(Status.OK)
                .with(htmlView(request)
                        of CharactersPageVM(fetchAllCharacters(), false))

        return Response(Status.OK)
            .with(htmlView(request)
                    of CharactersPageVM(findCharactersByUserID(user.userID), false))
    }
}

class NewCharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val model = NewCharacterPageVM()
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class CharacterCreationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        
        val valid = getValidData(request)

        if (valid.size < 4)
            return Response(Status.FOUND).header("Location", "/Characters")
        
        val name = valid[0]
        val characterClass = valid[1]
        val race = valid[2]
        val level = valid[3].toInt()

        //'user author' will be soon
        val character =
            Character(
                -1,
                5,
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
        val campID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1
        val userID = lensOrNull(userIdLens, request)?.toIntOrNull() ?: -1

        findCampaignByID(campID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${campID}/Users")

        val model = CharactersPageVM(findCharactersByUserID(userID), chooseFlag = true)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class ChooseCharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val campID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1
        val userID = lensOrNull(userIdLens, request)?.toIntOrNull() ?: -1

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
