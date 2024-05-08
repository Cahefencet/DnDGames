package config

import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.int

class AppConfig(val webPort: Int) {
    companion object {
        private val webPortLens = EnvironmentKey.int().required("web.port")

        private val defaultWebConfig =
            Environment.defaults(
                webPortLens of 9000,
            )

        fun readConfiguration(): AppConfig {
            val envConfig =
                Environment
                    .fromResource(
                        "ru/uniyar/config/app.properties",
                    )
                    .overrides(defaultWebConfig)

            return AppConfig(webPortLens(envConfig))
        }

        fun getEnvConfig(): Environment {
            return Environment
                .fromResource(
                    "ru/uniyar/config/app.properties",
                )
                .overrides(defaultWebConfig)
        }
    }
}
