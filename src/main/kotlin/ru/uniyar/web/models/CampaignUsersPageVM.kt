package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign
import ru.uniyar.db.UserCharacter
import ru.uniyar.utils.UserStruct

class CampaignUsersPageVM(
    val campaign: Campaign,
    val userCharacters : MutableList<UserCharacter>,
    val void : Boolean,
    val userStruct: UserStruct?,
) : ViewModel