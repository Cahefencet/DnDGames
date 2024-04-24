package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.auth.Role
import ru.uniyar.db.User

class RegistrationPageVM(
    val user: User?,
    val problem: Boolean?,
    val name: String?,
    val role: Role?,
    ) : ViewModel