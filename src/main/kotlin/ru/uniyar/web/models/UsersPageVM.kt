package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.User
import ru.uniyar.utils.UserStruct

class UsersPageVM(
    val users: List<User>,
    val adminFlag: Boolean,
    val userStruct: UserStruct?,
) : ViewModel