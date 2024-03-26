package ru.uniyar.db

import ru.uniyar.auth.Role

data class User(
    val userID: Int,
    val userName: String,
    val password: String,
    val role: Role,
)
