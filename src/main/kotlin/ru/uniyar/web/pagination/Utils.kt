package ru.uniyar.web.pagination

import org.http4k.core.Uri
import java.time.LocalDate

data class Page(val number: Int, val uri: Uri)

fun <T> pageAmount(
    lst: MutableList<T>,
    elementsOnPage: Int,
): Int {
    val res = lst.size / elementsOnPage
    if (res == 0) return 1
    return if (lst.size % elementsOnPage != 0) {
        res + 1
    } else {
        return res
    }
}

fun <T> filterByPageNumber(
    lst: MutableList<T>,
    elementsOnPage: Int,
    pageNumber: Int,
): MutableList<T> {
    val elsByPageNumber = mutableListOf<T>()
    lst.forEachIndexed { index, t ->
        if ((index / elementsOnPage) + 1 == pageNumber) {
            elsByPageNumber.add(t)
        }
    }
    return elsByPageNumber
}

fun getPaginationData(paginator: Paginator): PaginationData {
    var prev = paginator.getUri(1)
    var next = paginator.getUri(paginator.getTotal())

    if (paginator.hasNext()) {
        next = paginator.getNext().first().uri
    }
    if (paginator.hasPrev()) {
        prev = paginator.getPrev().first().uri
    }

    val cur =
        Page(
            paginator.getCur(),
            paginator.getUri(paginator.getCur()),
        )

    return PaginationData(
        paginator.getUri(1),
        paginator.getUri(paginator.getTotal()),
        cur,
        prev,
        next,
    )
}

data class PaginationData(
    val first: Uri,
    val last: Uri,
    val cur: Page,
    val prev: Uri,
    val next: Uri,
)

data class PostPaginationData(
    val date: LocalDate,
    val uri: Uri,
)

const val CAMPAIGNS_ON_PAGE = 6
const val CHARACTERS_ON_PAGE = 12
