package ru.uniyar

import config.ServerConfig
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import ru.uniyar.auth.Role
import ru.uniyar.db.User
import ru.uniyar.db.deleteUserById
import ru.uniyar.db.findUserByName
import ru.uniyar.db.insertUser
import ru.uniyar.utils.DBConnection

class DBTests : FunSpec({
    isolationMode = IsolationMode.SingleInstance
    DBConnection.connect(ServerConfig.readConfiguration())

    test("insert user test") {
        insertUser(
            User(
                0,
                "Test",
                "Test",
                Role.USER,
            ),
        )

        val newUser = findUserByName("Test")

        if (newUser != null) {
            newUser.userName.shouldBe("Test")
            newUser.password.shouldBe("Test")
            newUser.role.shouldBe(Role.USER)

            deleteUserById(newUser.userID)
        }
    }

    test("fetch user by valid name test") {
        insertUser(
            User(
                0,
                "Test",
                "Test",
                Role.USER,
            ),
        )

        val newUser = findUserByName("Test")

        newUser.shouldNotBe(null)

        if (newUser != null) {
            deleteUserById(newUser.userID)
        }
    }

    test("fetch user by not valid name test") {
        val notValidUser = findUserByName("Test")

        notValidUser.shouldBe(null)
    }

    test("delete user by id test") {
        insertUser(
            User(
                0,
                "Test",
                "Test",
                Role.USER,
            ),
        )

        val newUser = findUserByName("Test")

        if (newUser != null) {
            deleteUserById(newUser.userID)
        }

        findUserByName("Test").shouldBe(null)
    }

    test("delete user by not valid id test") {
        insertUser(
            User(
                0,
                "Test",
                "Test",
                Role.USER,
            ),
        )

        val newUser = findUserByName("Test")

        newUser.shouldNotBe(null)

        if (newUser != null) {
            deleteUserById(newUser.userID + 1)
        }

        findUserByName("Test").shouldNotBe(null)

        if (newUser != null) {
            deleteUserById(newUser.userID)
        }
    }
})
