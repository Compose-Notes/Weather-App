package dev.androidpoet.weatherapp.core.data.network

import Weather_App.composeApp.BuildConfig
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.time.Duration.Companion.seconds

object HttpClientFactory {

    fun create(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(HttpTimeout) {
                this.connectTimeoutMillis = 90.seconds.inWholeMilliseconds
                this.socketTimeoutMillis = 90.seconds.inWholeMilliseconds
                this.requestTimeoutMillis = 90.seconds.inWholeMilliseconds
            }

            defaultRequest {
                url(urlString = BuildConfig.BASE_URL)
                contentType(ContentType.Application.Json)
            }
        }
    }
}