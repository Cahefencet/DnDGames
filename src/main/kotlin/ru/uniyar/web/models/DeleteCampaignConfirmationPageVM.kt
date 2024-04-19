package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign

class DeleteCampaignConfirmationPageVM(val campaign: Campaign) : ViewModel