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

        private val url = "mysql://${dotenv.get("HOST")}:3306/dndgames"
        private val user = dotenv.get("USER")
        private val password = dotenv.get("PASSWORD")
        private val driver = "com.mysql.cj.jdbc.Driver"

        fun connect() {
            Database.connect(url, driver, user, password)
        }
    }
}
