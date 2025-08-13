package dev.androidpoet.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherApiResponse(
    val timezone: String,
    val timezoneOffset: Int,
    val current: CurrentWeather,
    val hourly: List<HourlyData> = emptyList(),
    val daily: List<DailyData> = emptyList()
)
