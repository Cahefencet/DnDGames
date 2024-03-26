package ru.uniyar.auth

import config.AppConfig
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.nonEmptyString
import java.security.MessageDigest
import java.util.HexFormat

class Hasher {
    companion object {
        private val commaFormat = HexFormat.of()
        private var messageDigest: MessageDigest = MessageDigest.getInstance("SHA-256") // caching-SHA-2
        private val saltLens = EnvironmentKey.nonEmptyString().required("auth.salt")

        fun hashPassword(password: String): String {
            val withSalt = password + AppConfig.getEnvConfig()[saltLens]
            val bytes = withSalt.toByteArray()
            val digest = messageDigest.digest(bytes)
            return commaFormat.formatHex(digest)
        }
    }
}
