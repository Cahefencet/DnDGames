package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign
import ru.uniyar.db.CampaignPost
import ru.uniyar.db.User

class CampaignPageVM(
    val campaign: Campaign,
    val master : User,
    val posts: List<CampaignPost>
) : ViewModel