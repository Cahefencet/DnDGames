package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.utils.UserStruct

class NewCampaignPageVM(
    val userStruct: UserStruct?
) : ViewModel