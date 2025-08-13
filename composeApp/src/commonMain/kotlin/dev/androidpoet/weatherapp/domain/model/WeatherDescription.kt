package dev.androidpoet.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDescription(
    val main: String,
    val description: String,
    val icon: String
)