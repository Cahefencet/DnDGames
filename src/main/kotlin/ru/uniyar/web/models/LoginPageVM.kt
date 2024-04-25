package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.User
import ru.uniyar.utils.UserStruct

class LoginPageVM(
    val user: User?,
    val userStruct: UserStruct?
) : ViewModel
