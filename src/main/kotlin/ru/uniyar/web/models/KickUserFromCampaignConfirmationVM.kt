package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign
import ru.uniyar.db.User

class KickUserFromCampaignConfirmationVM(val user: User, val campaign: Campaign) : ViewModel