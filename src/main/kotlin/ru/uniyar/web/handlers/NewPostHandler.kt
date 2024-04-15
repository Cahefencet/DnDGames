package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.CampaignPost
import ru.uniyar.db.Visibility
import ru.uniyar.db.findCampaignByID
import ru.uniyar.db.insertPost
import ru.uniyar.utils.htmlView
import ru.uniyar.web.models.NewPostVM
import java.time.LocalDate
import java.time.LocalDateTime

class NewPostHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val model = NewPostVM(campaign)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class PostCreationHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val valid = getValidData(request)
        if (valid.size < 3)
            return Response(Status.FOUND).header("Location","/Campaigns")

        val text = valid[2]
        val visibility = Visibility.valueOf(valid[1])
        val date = LocalDate.parse(valid[0])

        //'post author' will be soon
        val post = CampaignPost(
            -1,
            campaignID,
            3,
            text,
            visibility,
            date,
            LocalDateTime.now()
        )

        insertPost(post)

        return Response(Status.FOUND).header("Location","/Campaigns/${campaignID}")
    }

    private fun getValidData(request: Request) : MutableList<String> {

        val notValid = mutableListOf<String>()

        val form = request.form()

        val formDate = form.findSingle("date") ?: ""
        val formVisibility = form.findSingle("visibility") ?: ""
        val formText = form.findSingle("text") ?: ""

        val valid = mutableListOf<Any>(formDate, formVisibility, formText)

        if (valid.any { it == "" || it.equals(null)} || formText.length > 3500)
            return notValid

        try {
            val visibility = Visibility.valueOf(formVisibility)
            val date = LocalDate.parse(formDate)
            return mutableListOf(date.toString(), visibility.toString(), formText)
        } catch (e: IllegalArgumentException) {
            return notValid
        }
    }
}
