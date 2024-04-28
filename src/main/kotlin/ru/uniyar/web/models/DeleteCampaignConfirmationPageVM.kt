package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign
import ru.uniyar.utils.UserStruct

class DeleteCampaignConfirmationPageVM(
    val campaign: Campaign,
    val userStruct: UserStruct?,
) : ViewModel
