package ru.uniyar.utils

import io.github.cdimascio.dotenv.dotenv
import org.jetbrains.exposed.sql.Database

class DBConnection {
    companion object {
        private val dotenvPrivate =
            dotenv {
                directory = "./src/main/resources/ru/uniyar/config/"
                ignoreIfMalformed = true
                ignoreIfMissing = true
                filename = "DBAuth.env"
            }

        private val dotenvPublic =
            dotenv {
                directory = "./src/main/resources/ru/uniyar/config/"
                ignoreIfMalformed = true
                ignoreIfMissing = true
                filename = "DriverHost.env"
            }

        private val url = "jdbc:mysql://${dotenvPublic.get("HOST")}:3306/dndgames"
        private val password = dotenvPrivate.get("PASSWORD")
        private val driver = dotenvPublic.get("DRIVER")
        private val user = dotenvPrivate.get("DB_USER")


        fun connect() {
            Database.connect(url, driver, user, password)
        }
    }
}
