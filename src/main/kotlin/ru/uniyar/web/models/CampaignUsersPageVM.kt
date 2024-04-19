package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign
import ru.uniyar.db.UserCharacter

class CampaignUsersPageVM(val campaign: Campaign, val userCharacters : MutableList<UserCharacter>) : ViewModel