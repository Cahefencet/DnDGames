package ru.uniyar.web.handlers

import org.http4k.core.HttpHandler
import org.http4k.core.Status
import org.http4k.core.Response
import org.http4k.core.with
import org.http4k.core.Request
import ru.uniyar.auth.Role
import ru.uniyar.db.User
import ru.uniyar.db.fetchAllCampaigns
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.CampaignsPageVM

class CampaignsHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val user = User(
            3,
            "Example",
            "1234",
            Role.USER
        )


        val model = CampaignsPageVM(fetchAllCampaigns())
        return Response(Status.OK).with(htmlView(request) of model)
    }
}