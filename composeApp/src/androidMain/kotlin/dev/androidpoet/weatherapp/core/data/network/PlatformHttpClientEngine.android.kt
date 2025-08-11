package dev.androidpoet.weatherapp.core.data.network

import android.content.Context
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit

// TODO 00:47 12-Aug-2025: maybe we should add chuker for better debugging?
actual class PlatformHttpClientEngine(
    private val context: Context,
) {

    actual val engine: HttpClientEngine
        get() = OkHttp.create {
            config {
                connectTimeout(90, TimeUnit.SECONDS)
                readTimeout(90, TimeUnit.SECONDS)
                writeTimeout(90, TimeUnit.SECONDS)
            }
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }
}