package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.User

class UserPageVM(val user: User) : ViewModel