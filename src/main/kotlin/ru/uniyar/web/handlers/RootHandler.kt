package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.core.with
import ru.uniyar.utils.htmlView
import ru.uniyar.utils.userLens
import ru.uniyar.web.models.MainPageVM

class RootHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
        val model = MainPageVM()
        return Response(Status.OK).with(htmlView(request) of model)
    }
}
