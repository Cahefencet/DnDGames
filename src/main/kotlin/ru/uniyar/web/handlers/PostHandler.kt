package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.db.*
import ru.uniyar.utils.htmlView
import ru.uniyar.utils.userLens
import ru.uniyar.web.models.DeletePostConfirmationPageVM
import ru.uniyar.web.models.PostPageVM
import java.time.LocalDate
import java.time.LocalDateTime

class NewPostHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val campaignID = lensOrNull(campaignIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaign = findCampaignByID(campaignID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        //todo проверка на отношение usera к кампании, иначе создать пост нельзя


        val model = PostPageVM(campaign, null, null, userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class PostCreationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

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

        //todo проверка на отношение usera к кампании, иначе создать пост нельзя

        val post = CampaignPost(
            -1,
            campaignID,
            userStruct.id,
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

class EditPostConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val campID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1

        val campaign = findCampaignByID(campID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        val postID = lensOrNull(postIdLens, request)?.toIntOrNull() ?: -1

        val post = findPostByID(postID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        val author = findUserByID(post.authorID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        if (author.userID != userStruct.id)
            return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        //todo проверка на отношение к кампании

        val model = PostPageVM(campaign, post, post.text, userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class EditPostHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val campID = lensOrNull(campaignIdLens, request)?.toIntOrNull() ?: -1

        findCampaignByID(campID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns")

        val postID = lensOrNull(postIdLens, request)?.toIntOrNull() ?: -1

        val post = findPostByID(postID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        val author = findUserByID(post.authorID)
            ?: return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        //todo проверка на отношение к кампании
        if (userStruct.id != author.userID)
            return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        val form = request.form()

        val newDate = form.findSingle("date") ?: ""
        val newVis = form.findSingle("visibility") ?: ""
        val newText = form.findSingle("text") ?: ""

        if (newText.isEmpty()
            || newText.length > 3500
            || newDate == ""
            || newVis == "")
            return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        try {
            Visibility.valueOf(newVis)
            LocalDate.parse(newDate)
        } catch (e: IllegalArgumentException) {
            return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")

        }

        editPost(
            CampaignPost(
                post.postId,
                post.campaignID,
                post.authorID,
                newText,
                Visibility.valueOf(newVis),
                LocalDate.parse(newDate),
                post.postDate
            ))

        return Response(Status.FOUND).header("Location", "/Campaigns/${campID}")
    }
}

class DeletePostConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val postID = lensOrNull(postIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val post = findPostByID(postID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val author = findUserByID(post.authorID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        //todo проверка на отношение usera к кампании

        val model = DeletePostConfirmationPageVM(post, author, post.text, userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeletePostHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val requestPostID = lensOrNull(postIdLens, request)?.toIntOrNull() ?: -1

        val form = request.form()

        val formPostID = form.findSingle("postID")?.toIntOrNull() ?: -2

        if (requestPostID != formPostID)
            return Response(Status.FOUND).header("Location", "/Campaigns")

        val post = findPostByID(formPostID)
            ?: return Response(Status.FOUND).header("Location","/Campaigns")

        val campaignID = findCampIDByPostID(formPostID) ?: -1

        //todo проверка на отношение к кампании

        if (post.authorID == userStruct.id)
            deletePostByID(formPostID)

        return Response(Status.FOUND).header("Location", "/Campaigns/${campaignID}")
    }
}
