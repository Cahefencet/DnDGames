package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign

class DeleteCampaignConfirmationVM(val campaign: Campaign) : ViewModel