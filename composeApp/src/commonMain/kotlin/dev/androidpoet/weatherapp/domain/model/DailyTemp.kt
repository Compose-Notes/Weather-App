package dev.androidpoet.weatherapp.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class DailyTemp(
    val min: Double,
    val max: Double
)

