package ru.uniyar.utils

import config.ServerConfig
import org.jetbrains.exposed.sql.Database
import org.flywaydb.core.Flyway

class DBConnection {
    companion object {
        fun connect(configuration: ServerConfig) {
            val url = "jdbc:mysql://${configuration.dbHost}:3306/dndgames"

            Database.connect(
                url,
                configuration.dbDriver,
                configuration.dbUser,
                configuration.dbPass,
            )

            Flyway.configure()
                .dataSource("$url?permitMysqlScheme=true", configuration.dbUser, configuration.dbPass)
                .locations("filesystem:migrations")
                .load()
                .migrate()
        }
    }
}
