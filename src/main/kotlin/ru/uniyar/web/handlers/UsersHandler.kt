package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import org.http4k.core.cookie.invalidateCookie
import ru.uniyar.db.deleteUserById
import ru.uniyar.db.fetchAllAdministrators
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

        if (!(userStruct.role.manageUsers))
            return Response(Status.FOUND).header("Location", "/")

        val users = fetchAllUsers()
        val model = UsersPageVM(users, false, userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class AdministrationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageUsers))
            return Response(Status.FOUND).header("Location", "/")

        val administrators = fetchAllAdministrators()

        val model = UsersPageVM(administrators, true, userStruct)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteUserConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageUsers))
            return Response(Status.FOUND).header("Location", "/")

        val userID = lensOrNull(userIdLens, request)?.toIntOrNull() ?: -1

        val user = findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location", "/Users")

        val model = DeleteUserConfirmationPageVM(user, userStruct, false)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class DeleteUserHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageUsers))
            return Response(Status.FOUND).header("Location", "/")

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

class SelfDeleteConfirmationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        if (!(userStruct.role.manageUsers
            || userStruct.role.manageAllCampaigns
            || userStruct.role.manageAllCharacters
            || userStruct.role.manageOwnCampaigns))
            return Response(Status.FOUND).header("Location", "/")

        val user = findUserByID(userStruct.id)
            ?: return Response(Status.FOUND).header("Location", "/")

        val model = DeleteUserConfirmationPageVM(user, userStruct, true)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class SelfDeleteHandler : HttpHandler {
    override fun invoke(request: Request): Response {

        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val user = findUserByID(userStruct.id)
            ?: return Response(Status.FOUND).header("Location", "/")

        val formUserID = request.form().findSingle("userID")?.toIntOrNull() ?: -1

        if (userStruct.id != formUserID)
            return Response(Status.FOUND).header("Location", "/")

        deleteUserById(user.userID)

        return Response(Status.FOUND).invalidateCookie("auth").header("Location", "/")
    }
}
