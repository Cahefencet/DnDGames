package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Character
import ru.uniyar.utils.UserStruct
import ru.uniyar.web.pagination.PaginationData

class CharactersPageVM(
    val characters: List<Character>,
    val chooseFlag: Boolean,
    val userStruct: UserStruct?,
    val paginationData: PaginationData,
) : ViewModel
