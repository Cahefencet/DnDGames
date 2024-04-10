package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign

class CampaignsPageVM(val campaigns: MutableList<Campaign>) : ViewModel