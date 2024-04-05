package ru.uniyar.utils

import ru.uniyar.auth.Role

fun stringToUserRole(str: String) : Role {
    return when(str) {
        "USER" -> Role.USER
        "REDACTOR" -> Role.REDACTOR
        "MODERATOR" -> Role.MODERATOR
        "ADMIN" -> Role.ADMIN
        else -> Role.ANONYMOUS
    }
}