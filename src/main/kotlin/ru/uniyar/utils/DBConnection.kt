package ru.uniyar.utils

import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database

class DBConnection {
    companion object {
        private val dotenv =
            dotenv {
                directory = "./src/main/resources/ru/uniyar/config/"
                ignoreIfMalformed = true
                ignoreIfMissing = true
            }

        private val url = "jdbc:mysql://${dotenv.get("HOST")}:3306/dndgames"
        private val password = dotenv.get("PASSWORD")
        private val driver = dotenv.get("DRIVER")

        fun connect() {
            Database.connect(url, driver, user = "root", password)
        }
    }
}