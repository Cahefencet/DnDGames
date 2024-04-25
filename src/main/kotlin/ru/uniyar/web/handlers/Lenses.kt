package ru.uniyar.web.handlers

import org.http4k.lens.Lens
import org.http4k.lens.LensFailure
import org.http4k.lens.Path
import org.http4k.lens.nonBlankString

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
val characterIdLens = Path.nonBlankString().of("characterID")
val postIdLens = Path.nonBlankString().of("postID")
