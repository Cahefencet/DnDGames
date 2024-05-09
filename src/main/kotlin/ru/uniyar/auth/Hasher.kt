package ru.uniyar.auth

import config.ServerConfig
import java.security.MessageDigest
import java.util.HexFormat

class Hasher {
    companion object {
        private val commaFormat = HexFormat.of()
        private var messageDigest: MessageDigest = MessageDigest.getInstance("SHA-256") // caching-SHA-2
        private val config = ServerConfig.readConfiguration()

        fun hashPassword(password: String): String {
            val withSalt = password + config.salt
            val bytes = withSalt.toByteArray()
            val digest = messageDigest.digest(bytes)
            return commaFormat.formatHex(digest)
        }
    }
}
