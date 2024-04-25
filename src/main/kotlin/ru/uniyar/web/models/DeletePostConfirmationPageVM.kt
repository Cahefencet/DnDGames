package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.CampaignPost
import ru.uniyar.db.User
import ru.uniyar.utils.UserStruct

class DeletePostConfirmationPageVM(
    val post: CampaignPost,
    val author : User,
    val text : String,
    val userStruct: UserStruct?,
) : ViewModel