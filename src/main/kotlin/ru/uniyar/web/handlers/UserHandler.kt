package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import ru.uniyar.auth.Hasher
import ru.uniyar.auth.Role
import ru.uniyar.db.User
import ru.uniyar.db.findUserByID
import ru.uniyar.db.findUserByName
import ru.uniyar.db.insertUser
import ru.uniyar.utils.htmlView
import ru.uniyar.utils.userLens
import ru.uniyar.web.models.RegistrationPageVM
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

class RegistrationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
        val model = RegistrationPageVM(null, null, userStruct?.name, userStruct?.role)
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class UserCreationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val form = request.form()

        val name = form.findSingle("name") ?: ""
        val pass = form.findSingle("password") ?: ""
        val conf = form.findSingle("passwordConfirmation") ?: ""
        val wrongData = User(-1, "wrong data", "1234", Role.ANONYMOUS)
        val userStruct = userLens(request)
        var model = RegistrationPageVM(wrongData, false, userStruct?.name, userStruct?.role)


        if (valid(name, pass, conf)) {
            if (findUserByName(name) != null){
                model = RegistrationPageVM(wrongData, true, userStruct?.name, userStruct?.role)
                return Response(Status.OK).with(htmlView(request) of model)
            }
            insertUser(User(-1, name, Hasher.hashPassword(pass), Role.USER))
            return Response(Status.FOUND).header("Location", "/Login")
        }

        return Response(Status.OK).with(htmlView(request) of model)
    }

    fun valid(
        name: String,
        pass: String,
        conf: String,
    ): Boolean {
        if (name.isEmpty() || pass.isEmpty() || conf.isEmpty()) {
            return false
        }
        return pass == conf
    }
}
