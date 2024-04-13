package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Character

class CharactersPageVM(val characters: List<Character>) : ViewModel