package dev.androidpoet.weatherapp.core.data.network

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual class PlatformHttpClientEngine {

    actual val engine: HttpClientEngine
        get() = Darwin.create()
}