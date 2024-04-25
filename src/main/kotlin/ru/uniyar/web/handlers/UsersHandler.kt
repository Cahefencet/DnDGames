package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.auth.Role
import ru.uniyar.db.deleteUserById
import ru.uniyar.db.fetchAllUsers
import ru.uniyar.db.findUserByID
import ru.uniyar.utils.htmlView
import ru.uniyar.utils.userLens
import ru.uniyar.web.models.DeleteUserConfirmationPageVM
import ru.uniyar.web.models.UsersPageVM

class UsersHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")
//        if (userStruct.role != Role.ADMIN)
//            return Response(Status.FOUND).header("Location", "/")
        val users = fetchAllUsers()
        val model = UsersPageVM(users, userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteUserConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")
        // admin
        val userID = lensOrNull(userIdLens, request)?.toIntOrNull() ?: -1
        val user = findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location", "/Users")
        val model = DeleteUserConfirmationPageVM(user, userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteUserHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        //todo проверить role здесь и в аналогичных ситуациях

        val form = request.form()
        val formUserID = form.findSingle("userID")?.toIntOrNull() ?: -1

        val requestUserID = lensOrNull(userIdLens, request)?.toIntOrNull() ?: -2

        if (formUserID != requestUserID)
            return Response(Status.FOUND).header("Location", "/Users")

        val user = findUserByID(requestUserID)
            ?: return Response(Status.FOUND).header("Location", "/Users")

        deleteUserById(user.userID)
        return Response(Status.FOUND).header("Location", "/Users")
    }
}
