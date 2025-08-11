package dev.androidpoet.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyData(
    val dt: Long,
    val temp: DailyTemp,
    val weather: List<WeatherDescription>,
    val summary: String? = null,
)

