package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign
import ru.uniyar.db.CampaignPost
import ru.uniyar.db.User
import ru.uniyar.utils.UserStruct
import ru.uniyar.web.pagination.PostPaginationData

class CampaignPageVM(
    val campaign: Campaign,
    val master : User,
    val posts: List<CampaignPost>,
    val userStruct: UserStruct?,
    val paginationData: List<PostPaginationData>,
    val paginationFlag: Boolean,
) : ViewModel