package config

import org.http4k.cloudnative.env.Environment

class AppConfig(val webConfig: WebConfig) {
    companion object {
        private val defaultWebConfig =
            Environment.defaults(
                WebConfig.webPortLens of 9000,
            )

        fun readConfiguration(): AppConfig {
            val envConfig =
                Environment
                    .fromResource(
                        "ru/uniyar/config/app.properties",
                    )
                    .overrides(Environment.JVM_PROPERTIES)
                    .overrides(Environment.ENV)
                    .overrides(defaultWebConfig)

            return AppConfig(WebConfig.createWebConfig(envConfig))
        }

        fun getEnvConfig(): Environment {
            return Environment
                .fromResource(
                    "ru/uniyar/config/app.properties",
                )
                .overrides(Environment.JVM_PROPERTIES)
                .overrides(Environment.ENV)
                .overrides(defaultWebConfig)
        }
    }
}
