package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Character

class CharacterPageVM(val character: Character) : ViewModel