package dev.androidpoet.weatherapp.domain.viewmodel

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.androidpoet.weatherapp.domain.model.DailyData
import dev.androidpoet.weatherapp.domain.model.DailyTemp
import dev.androidpoet.weatherapp.domain.model.DailyWeatherRowModel
import dev.androidpoet.weatherapp.domain.model.WeatherDescription
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import weather_app.composeapp.generated.resources.Res
import weather_app.composeapp.generated.resources.weather_clouds
import weather_app.composeapp.generated.resources.weather_precipitate
import kotlin.math.max
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

class WeatherForeCastViewModel : ViewModel(){

    var job: Job?=null

    val _sevenDayForeCast = MutableStateFlow<SevenDayWeatherUiState>(SevenDayWeatherUiState(emptyList()))
    val sevenDayForeCast :StateFlow<SevenDayWeatherUiState> = _sevenDayForeCast

    @OptIn(ExperimentalTime::class)
    fun getSevenDayForeCast(response : List<DailyData>) {
        job?.cancel()

        job = viewModelScope.launch {
            _sevenDayForeCast.value  = SevenDayWeatherUiState(emptyList())
            val list = mutableListOf<DailyWeatherRowModel>()
            response.forEach { data ->
                val img = if(data.weather.first().main.contains("rain",true)) Res.drawable.weather_clouds else Res.drawable.weather_precipitate

                val text = Instant.fromEpochMilliseconds(data.dt).toLocalDateTime(TimeZone.currentSystemDefault()).dayOfWeek.name.substring(0,3).lowercase()
                val currDay = text.substring(0,1).uppercase()+text.substring(1,3)

                val temp = (data.temp.max - 273.15).toInt().toString()+"° C"

                val isToday = Instant.fromEpochMilliseconds(data.dt).toLocalDateTime(TimeZone.currentSystemDefault()).date == Clock.System.todayIn(TimeZone.currentSystemDefault())
                val linearGradient = if(isToday) listOf(Color(0xFF3E2D8F),Color(0xFF9D52AC)) else listOf(Color(0xFF3E2D8F),Color(0xFF8E78C8))


                list.add(DailyWeatherRowModel(
                    dt=data.dt,
                    img = img ,
                    day = currDay,
                    temp = temp,
                    gradient = linearGradient
                ))
            }
            _sevenDayForeCast.value = SevenDayWeatherUiState(list)
        }
    }


    init {
    }





}



data class SevenDayWeatherUiState(
    val sevenDayForeCast: List<DailyWeatherRowModel>
)