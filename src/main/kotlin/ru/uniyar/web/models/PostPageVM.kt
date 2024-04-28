package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.CampaignPost
import ru.uniyar.utils.UserStruct

class PostPageVM(
    val ownerID: Int,
    val post: CampaignPost?,
    val text: String?,
    val userStruct: UserStruct?,
) : ViewModel
