package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Status
import org.http4k.core.Response
import org.http4k.core.with
import org.http4k.core.Request
import ru.uniyar.db.findUserByID
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.UserPageVM

class UserHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val userID = lensOrNull(userIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/")

        val user = findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location","/")

        val model = UserPageVM(user)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}