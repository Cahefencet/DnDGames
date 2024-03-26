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

        private val url = String.format("jdbc:mysql://%s/dndgames", dotenv.get("HOST"))
        private val user: String = dotenv.get("USER")
        private val password: String = dotenv.get("PASSWORD")

        fun connect() {
            Database.connect(
                url,
                driver = "org.mariadb.jdbc.Driver",
                user,
                password,
            )
        }
    }
}
