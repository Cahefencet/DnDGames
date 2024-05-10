package config

import io.github.cdimascio.dotenv.dotenv

class ServerConfig(
    val webPort: Int,
    val dbHost: String,
    val dbDriver: String,
    val dbUser: String,
    val dbPass: String,
    val salt: String,
    val secret: String,
) {
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
                filename = "DriverHostPort.env"
            }

        private val dotenvAuth =
            dotenv {
                directory = "./src/main/resources/ru/uniyar/config/"
                ignoreIfMalformed = true
                ignoreIfMissing = true
                filename = "Secret.env"
            }

        fun readConfiguration(): ServerConfig {
            val port =
                dotenvPublic.get("PORT")?.toIntOrNull() ?: 9000
            val driver =
                dotenvPublic.get("DRIVER") ?: "com.mysql.cj.jdbc.Driver"
            val dbUser =
                dotenvPrivate.get("DB_USER") ?: "user"
            val password =
                dotenvPrivate.get("PASSWORD")
            val host =
                dotenvPublic.get("HOST")
            val salt =
                dotenvAuth.get("auth.salt")
            val secret =
                dotenvAuth.get("secret")

            return ServerConfig(
                port,
                host,
                driver,
                dbUser,
                password,
                salt,
                secret,
            )
        }
    }
}
