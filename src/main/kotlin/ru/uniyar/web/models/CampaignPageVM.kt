package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign
import ru.uniyar.db.CampaignPost

class CampaignPageVM(val campaign: Campaign, val posts: List<CampaignPost>) : ViewModel