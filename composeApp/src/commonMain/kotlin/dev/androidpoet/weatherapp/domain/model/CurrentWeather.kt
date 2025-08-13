package dev.androidpoet.weatherapp.domain.model

import kotlinx.serialization.Serializable


@Serializable
data class CurrentWeather(
    val temp: Double,
    val sunrise: Long,
    val sunset: Long,
    val uvi: Double,
    val weather: List<WeatherDescription>
)

