package config

import org.http4k.cloudnative.env.Environment
import org.http4k.cloudnative.env.EnvironmentKey
import org.http4k.lens.int

class WebConfig(
    val webPort: Int,
) {
    companion object {
        val webPortLens = EnvironmentKey.int().required("web.port")

        fun createWebConfig(environment: Environment): WebConfig {
            return WebConfig(
                webPortLens(
                    environment,
                ),
            )
        }
    }
}
