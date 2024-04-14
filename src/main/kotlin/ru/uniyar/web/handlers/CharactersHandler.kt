package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.auth.Role
import ru.uniyar.db.*
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CharactersPageVM
import ru.uniyar.web.models.NewCharacterVM

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
                        of CharactersPageVM(fetchAllCharacters()))

        return Response(Status.OK)
            .with(htmlView(request)
                    of CharactersPageVM(findCharactersByUser(user.userID)))
    }
}

class NewCharacterHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val model = NewCharacterVM()
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class CharacterCreationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val form = request.form()
        val name = form.findSingle("name") ?: ""
        val characterClass = form.findSingle("characterClass") ?: ""
        val race = form.findSingle("race") ?: ""
        val level = form.findSingle("level") ?: ""

        val valid = mutableListOf<Any>()
        valid.add(name)
        valid.add(characterClass)
        valid.add(race)
        valid.add(level)
        if (valid.all { it == "" })
            return Response(Status.FOUND).header("Location", "/Characters")
        if (level.toInt() > 20 || level.toInt() < 1)
            return Response(Status.FOUND).header("Location", "/Characters")


        val character =
            Character(
                0,
                3,
                name,
                characterClass,
                race,
                level.toInt()
            )
        insertCharacter(character)

        val id = findCharacterIDByNameUserClassRaceLevel(character)

        return Response(Status.FOUND).header("Location", "/Characters/${id}")
    }
}