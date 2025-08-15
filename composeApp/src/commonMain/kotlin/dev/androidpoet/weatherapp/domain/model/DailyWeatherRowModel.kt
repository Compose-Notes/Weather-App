package dev.androidpoet.weatherapp.domain.model

import org.jetbrains.compose.resources.DrawableResource

data class DailyWeatherRowModel(
    val dt:Long,
    val img: DrawableResource,
    val day: String,
    val temp: String,
    val gradient: List<androidx.compose.ui.graphics.Color>
)