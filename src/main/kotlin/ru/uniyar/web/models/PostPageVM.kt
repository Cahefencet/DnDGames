package ru.uniyar.web.models

import org.http4k.template.ViewModel
import org.w3c.dom.Text
import ru.uniyar.db.Campaign
import ru.uniyar.db.CampaignPost

class PostPageVM(val campaignID : Campaign, val post : CampaignPost?, val text : String?) : ViewModel