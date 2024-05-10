package ru.uniyar.utils

import config.ServerConfig
import org.jetbrains.exposed.sql.Database

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
        }
    }
}
