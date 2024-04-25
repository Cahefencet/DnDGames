package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Character
import ru.uniyar.utils.UserStruct

class CharactersPageVM(
    val characters: List<Character>,
    val chooseFlag : Boolean,
    val userStruct: UserStruct,
) : ViewModel