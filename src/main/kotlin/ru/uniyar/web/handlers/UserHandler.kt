package ru.uniyar.web.handlers

import org.http4k.core.*
import org.http4k.core.body.form
import org.http4k.core.cookie.Cookie
import org.http4k.core.cookie.cookie
import org.http4k.core.cookie.invalidateCookie
import ru.uniyar.auth.Hasher
import ru.uniyar.auth.Role
import ru.uniyar.db.User
import ru.uniyar.db.findUserByID
import ru.uniyar.db.findUserByName
import ru.uniyar.db.insertUser
import ru.uniyar.utils.htmlView
import ru.uniyar.utils.jwtTools
import ru.uniyar.utils.userLens
import ru.uniyar.web.models.LoginPageVM
import ru.uniyar.web.models.RegistrationPageVM
import ru.uniyar.web.models.UserPageVM
import java.time.LocalDateTime
import java.time.ZoneOffset

class UserHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
            ?: return Response(Status.FOUND).header("Location", "/")

        val userID = lensOrNull(userIdLens, request)?.toIntOrNull()
            ?: return Response(Status.FOUND).header("Location","/")

        val user = findUserByID(userID)
            ?: return Response(Status.FOUND).header("Location","/")

        val model = UserPageVM(user, userStruct)

        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class RegistrationHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val userStruct = userLens(request)
        val model = RegistrationPageVM(null, null, userStruct)
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
        var model = RegistrationPageVM(wrongData, false, userStruct)


        if (valid(name, pass, conf)) {
            if (findUserByName(name) != null){
                model = RegistrationPageVM(wrongData, true, userStruct)
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

class LoginFormHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val model = LoginPageVM(null, userLens(request))
        return Response(Status.OK).with(htmlView(request) of model)
    }
}

class LoginHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        val form = request.form()

        val name = form.findSingle("name") ?: ""
        val password = form.findSingle("password") ?: ""


        if (validate(name, password)) {
            if (findUserByName(name) != null) {
                val user = findUserByName(name)!!
                if (user.password == Hasher.hashPassword(password)) {
                    val token = jwtTools.createUserJwt(user.userID.toString())
                    val toDate = LocalDateTime.now().plusDays(7)
                    val cookie = Cookie("auth", token, expires = toDate.toInstant(ZoneOffset.of("+03:00")))

                    return Response(Status.FOUND).cookie(cookie).header("Location", "/Campaigns")
                }
            }
        }

        val wrongData = User(-1, name, "", Role.ANONYMOUS)
        val model = LoginPageVM(wrongData, userLens(request))
        return Response(Status.OK).with(htmlView(request) of model)
    }

    private fun validate(
        name: String,
        password: String,
    ): Boolean {
        return !(
                name.isEmpty()
                || password.isEmpty()
                || name == ""
                || password == ""
                || name.length > 100
                )
    }
}

class LogoutHandler : HttpHandler {
    override fun invoke(request: Request): Response {
        return Response(Status.FOUND).invalidateCookie("auth").header("Location", "/")
    }
}
