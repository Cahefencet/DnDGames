package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign
import ru.uniyar.db.User
import ru.uniyar.utils.UserStruct


// val selfFlag : Boolean

class KickUserFromCampaignConfirmationPageVM(
    val user: User,
    val campaign: Campaign,
    val userStruct: UserStruct?,
) : ViewModel