package ru.uniyar.utils

import config.ServerConfig
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import ru.uniyar.auth.Hasher
import ru.uniyar.auth.Role
import ru.uniyar.db.User
import ru.uniyar.db.insertUser

class DBConnection {
    companion object {
        fun connect(configuration: ServerConfig) {
            val url = "jdbc:mysql://${configuration.dbHost}:3306/${configuration.dbName}"

            Database.connect(
                url,
                configuration.dbDriver,
                configuration.dbUser,
                configuration.dbPass,
            )

            Flyway.configure()
                .dataSource("$url?permitMysqlScheme=true", configuration.dbUser, configuration.dbPass)
                .locations("filesystem:migrations")
                .target("1")
                .load()
                .migrate()

//    // first admin creation
//    insertUser(
//        User(
//            0,
//            "admin",
//            Hasher.hashPassword("12345"),
//            Role.ADMIN,
//        ),
//    )
        }
    }
}
