package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.deleteCharacterByID
import ru.uniyar.db.deleteUserById
import ru.uniyar.db.findCharacterByID
import ru.uniyar.db.findUserByID
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CharacterPageVM
import ru.uniyar.web.models.DeleteCharacterConfirmationVM

class CharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val characterID = lensOrNull(characterIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location", "Characters")

        val character = findCharacterByID(characterID)
            ?: return Response(Status.FOUND).header("Location", "Characters")

        val owner = findUserByID(character.userID)
            ?: return Response(Status.FOUND).header("Location", "Characters")

        val model = CharacterPageVM(character, owner)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteCharacterConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val characterID = lensOrNull(characterIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location", "Characters")

        val character = findCharacterByID(characterID)
            ?: return Response(Status.FOUND).header("Location", "Characters")

        val model = DeleteCharacterConfirmationVM(character)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteCharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val requestCharacterID = lensOrNull(characterIdLens, request)?.toIntOrNull() ?: -1

        val form = request.form()

        val formCharacterID = form.findSingle("charId")?.toIntOrNull() ?: -2

        if (requestCharacterID != formCharacterID)
            return Response(Status.FOUND).header("Location", "/Characters")

        val character = findCharacterByID(formCharacterID)
            ?: return Response(Status.FOUND).header("Location", "/Characters")

        // coming soon
        val userID = 3

        if (character.userID == userID)
            deleteCharacterByID(formCharacterID)

        return Response(Status.FOUND).header("Location", "/Characters")
    }
}
