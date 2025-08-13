package dev.androidpoet.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class HourlyData(
    val dt: Long,
    val temp: Double,
    val weather: List<WeatherDescription>,
    val pop: Double
)

