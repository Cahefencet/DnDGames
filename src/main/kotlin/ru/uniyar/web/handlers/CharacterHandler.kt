package ru.uniyar.web.handlers

import org.http4k.core.*
import ru.uniyar.db.findCharacterByID
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CharacterPageVM

class CharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val characterID = lensOrNull(characterIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location", "Characters")

        val character = findCharacterByID(characterID)
            ?: return Response(Status.FOUND).header("Location", "Characters")

        val model = CharacterPageVM(character)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}
