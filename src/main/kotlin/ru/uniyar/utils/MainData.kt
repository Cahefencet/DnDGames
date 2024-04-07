package ru.uniyar.utils

import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.core.ContentType
import org.http4k.core.Filter
import org.http4k.core.HttpHandler
import org.http4k.core.NoOp
import org.http4k.core.RequestContexts
import org.http4k.core.cookie.cookie
import org.http4k.core.then
import org.http4k.core.with
import org.http4k.filter.ServerFilters
import org.http4k.lens.RequestContextKey
import org.http4k.lens.RequestContextLens
import org.http4k.lens.string
import org.http4k.routing.RoutingHttpHandler
import ru.uniyar.auth.JwtTools
import ru.uniyar.auth.Role
import ru.uniyar.auth.Users
import ru.uniyar.web.router
import ru.uniyar.web.templates.ContextAwarePebbleTemplates
import ru.uniyar.web.templates.ContextAwareViewRender

data class UserStruct(val id: Int, val name: String, val role: Role)

val contexts = RequestContexts()
val userLens: RequestContextLens<UserStruct?> = RequestContextKey.optional(contexts, "user-struct")
val renderer = ContextAwarePebbleTemplates().hotReload("src/main/resources")
val contextAwareViewRender = ContextAwareViewRender.withContentType(renderer, ContentType.TEXT_HTML)
val roleLens: RequestContextLens<Role> = RequestContextKey.required(contexts, "user-role")
val htmlView =
    contextAwareViewRender
        .associateContextLens("userStruct", userLens)
        .associateContextLens("userRole", roleLens)

val jwtTools =
    JwtTools(
        EnvironmentKey.string().required("secret").toString(),
        "ru.example",
        7,
    )

fun getApp(users: Users): RoutingHttpHandler {
    fun authFilter(key: RequestContextLens<UserStruct?>) =
        Filter {
                next: HttpHandler ->
            exec@{
                    request ->
                val cookie = request.cookie("auth")
                val token = cookie?.value ?: return@exec next(request)
                val id = jwtTools.verifyToken(token) ?: return@exec next(request)
                val userStruct = users.getUserStructById(id.toInt()) ?: return@exec next(request)
                next(request.with(key of userStruct))
            }
        }

    fun roleFilter(
        key: RequestContextLens<UserStruct?>,
        roleLens: RequestContextLens<Role>,
    ) = Filter {
            next: HttpHandler ->
        exec@{ request ->
            val user = key(request) ?: return@exec next(request.with(roleLens of Role.ANONYMOUS))
            val role = user.role
            next(request.with(roleLens of role))
        }
    }

    val app =
        ServerFilters.InitialiseRequestContext(contexts)
            .then(authFilter(userLens))
            .then(roleFilter(userLens, roleLens))
            .then(router())

    return Filter.NoOp.then(app)
}
