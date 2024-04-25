package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.Character
import ru.uniyar.db.deleteCharacterByID
import ru.uniyar.db.editCharacter
import ru.uniyar.db.findCharacterByID
import ru.uniyar.db.findUserByID
import ru.uniyar.utils.htmlView
import ru.uniyar.utils.userLens
import ru.uniyar.web.models.CharacterPageVM
import ru.uniyar.web.models.DeleteCharacterConfirmationPageVM
import ru.uniyar.web.models.EditCharacterPageVM

class CharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val characterID = lensOrNull(characterIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location", "/Characters")

        val character = findCharacterByID(characterID)
            ?: return Response(Status.FOUND).header("Location", "/Characters")

        val owner = findUserByID(character.userID)
            ?: return Response(Status.FOUND).header("Location", "/Characters")

        val model = CharacterPageVM(character, owner, userStruct)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteCharacterConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val characterID = lensOrNull(characterIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location", "/Characters")

        val character = findCharacterByID(characterID)
            ?: return Response(Status.FOUND).header("Location", "/Characters")

        val model = DeleteCharacterConfirmationPageVM(character, userStruct)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteCharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val requestCharacterID = lensOrNull(characterIdLens, request)?.toIntOrNull() ?: -1

        val form = request.form()

        val formCharacterID = form.findSingle("charId")?.toIntOrNull() ?: -2

        if (requestCharacterID != formCharacterID)
            return Response(Status.FOUND).header("Location", "/Characters")

        val character = findCharacterByID(formCharacterID)
            ?: return Response(Status.FOUND).header("Location", "/Characters")

        if (character.userID == userStruct.id)
            deleteCharacterByID(formCharacterID)

        return Response(Status.FOUND).header("Location", "/Characters")
    }
}

class EditCharacterConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val charID = lensOrNull(characterIdLens, request)?.toIntOrNull() ?: -1
        val character = findCharacterByID(charID)
            ?: return Response(Status.FOUND).header("Location", "/Characters")

        if (userStruct.id != character.userID)
            return Response(Status.FOUND).header("Location", "/Characters/${charID}")

        val model = EditCharacterPageVM(character, userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class EditCharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val requestCharId = lensOrNull(characterIdLens, request)?.toIntOrNull() ?: -1
        val form = request.form()

        val formCharId = form.findSingle("charId")?.toIntOrNull() ?: -2

        if (requestCharId != formCharId)
            return Response(Status.FOUND).header("Location", "/Characters")

        val character = findCharacterByID(formCharId)
            ?: return Response(Status.FOUND).header("Location", "/Characters")

        val newName = form.findSingle("newName") ?: ""
        val newClass = form.findSingle("newClass") ?: ""
        val newRace = form.findSingle("newRace") ?: ""
        val newLevel = form.findSingle("newLevel")?.toIntOrNull() ?: 0

        if (character.userID != userStruct.id
            || newRace.length > 50
            || newName.length > 100
            || newClass.length > 100
            || newLevel > 20
            || newLevel < 1
            || newRace.isEmpty()
            || newName.isEmpty()
            || newClass.isEmpty())
            return Response(Status.FOUND).header("Location", "/Characters/${requestCharId}")

        editCharacter(
            Character(
                character.characterID,
                character.userID,
                newName, newClass,
                newRace, newLevel
        ))

        return Response(Status.FOUND).header("Location", "/Characters/${requestCharId}")
    }
}
