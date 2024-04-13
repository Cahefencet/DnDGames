package ru.uniyar.web.handlers

import org.http4k.lens.*

fun <IN : Any, OUT> lensOrNull(
    lens: Lens<IN, OUT?>,
    value: IN,
): OUT? =
    try {
        lens.invoke(value)
    } catch (_: LensFailure) {
        null
    }

val campaignIdLens = Path.nonBlankString().of("campaignID")
val userIdLens = Path.nonBlankString().of("userID")