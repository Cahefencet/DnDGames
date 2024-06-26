package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Character
import ru.uniyar.utils.UserStruct

class EditCharacterPageVM(
    val character: Character,
    val userStruct: UserStruct?,
) : ViewModel
