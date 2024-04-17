package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.CampaignPost
import ru.uniyar.db.User

class DeletePostConfirmationVM(val post: CampaignPost, val author : User) : ViewModel