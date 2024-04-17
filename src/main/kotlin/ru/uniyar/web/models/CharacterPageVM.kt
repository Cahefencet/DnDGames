package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Character
import ru.uniyar.db.User

class CharacterPageVM(val character: Character, val owner: User) : ViewModel