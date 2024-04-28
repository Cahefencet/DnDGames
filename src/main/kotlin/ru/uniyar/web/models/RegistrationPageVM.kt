package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.User
import ru.uniyar.utils.UserStruct

class RegistrationPageVM(
    val user: User?,
    val problem: Boolean?,
    val userStruct: UserStruct?,
    val adminFlag: Boolean,
) : ViewModel
