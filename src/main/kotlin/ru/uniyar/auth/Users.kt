package ru.uniyar.auth

import ru.uniyar.utils.UserStruct
import java.util.UUID

data class User(val name: String, val password: String, val id: UUID, val role: Role)

class Users(userList: List<User>) {
    private val users = mutableListOf<User>()

    init {
        userList.forEach {
            users.add(it)
        }
    }

    private fun fetchUserById(id: String): User? {
        return users.find { it.id.toString() == id }
    }

    fun getUserStructById(id: String): UserStruct? {
        val user = fetchUserById(id)
        if (user != null) {
            return UserStruct(user.id, user.name, user.role)
        }
        return null
    }
}
