package ru.uniyar.web.models

import org.http4k.template.ViewModel
import ru.uniyar.db.Campaign
import ru.uniyar.utils.UserStruct
import ru.uniyar.web.pagination.PaginationData

class CampaignsPageVM(
    val campaigns: MutableList<Campaign>,
    val userStruct: UserStruct?,
    val paginationData: PaginationData,
) : ViewModel
