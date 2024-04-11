package ru.uniyar.auth

import ru.uniyar.db.User
import ru.uniyar.utils.UserStruct

class Users(userList: List<User>) {
    private val users = mutableListOf<User>()

    init {
        userList.forEach {
            users.add(it)
        }
    }

    private fun fetchUserById(id: Int): User? {
        return users.find { it.userID == id }
    }

    fun getUserStructById(id: Int): UserStruct? {
        val user = fetchUserById(id)
        if (user != null) {
            return UserStruct(user.userID, user.userName, user.role)
        }
        return null
    }
}
