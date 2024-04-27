package ru.uniyar.web.pagination

import org.http4k.core.Uri
import org.http4k.core.query

class Paginator(
    private val uri: Uri,
    private val cur: Int,
    private val total: Int
) {

    fun hasPrev(): Boolean {
        return cur in 2..total
    }

    fun getPrev(): List<Page> {
        val prev = mutableListOf<Page>()
        for (pageNum in cur - 1 downTo 1)
            prev.add(Page(pageNum, getUri(pageNum)))
        return prev
    }

    fun hasNext(): Boolean {
        return cur in 1 until total
    }

    fun getNext(): List<Page> {
        val next = mutableListOf<Page>()
        for (pageNum in cur + 1..total)
            next.add(Page(pageNum, getUri(pageNum)))
        return next
    }

    fun getUri(page: Int): Uri {
        return uri.query("page", page.toString())
    }

    fun getCur(): Int {
        return this.cur
    }

    fun getTotal(): Int {
        return this.total
    }
}
