package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.User

class DeleteUserConfirmationPageVM(val user: User) : ViewModel