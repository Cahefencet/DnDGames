package ru.uniyar.web.handlers

import org.http4k.core.*
import ru.uniyar.auth.Role
import ru.uniyar.db.User
import ru.uniyar.db.fetchAllCharacters
import ru.uniyar.db.findCharactersByUser
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CharactersPageVM

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