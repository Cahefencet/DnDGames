package ru.uniyar.auth

import ru.uniyar.utils.UserStruct

data class User(val name: String, val password: String, val id: Int, val role: Role)

class Users(userList: List<User>) {
    private val users = mutableListOf<User>()

    init {
        userList.forEach {
            users.add(it)
        }
    }

    private fun fetchUserById(id: Int): User? {
        return users.find { it.id == id }
    }

    fun getUserStructById(id: Int): UserStruct? {
        val user = fetchUserById(id)
        if (user != null) {
            return UserStruct(user.id, user.name, user.role)
        }
        return null
    }
}
