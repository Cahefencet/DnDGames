package ru.uniyar.web.handlers

import org.http4k.core.*
import ru.uniyar.db.fetchAllUsers
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.UsersPageVM

class UsersHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val users = fetchAllUsers()
        val model = UsersPageVM(users)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}