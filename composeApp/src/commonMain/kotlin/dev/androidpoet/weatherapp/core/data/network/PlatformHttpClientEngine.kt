package dev.androidpoet.weatherapp.core.data.network

import io.ktor.client.engine.HttpClientEngine

expect class PlatformHttpClientEngine {
    val engine: HttpClientEngine
}