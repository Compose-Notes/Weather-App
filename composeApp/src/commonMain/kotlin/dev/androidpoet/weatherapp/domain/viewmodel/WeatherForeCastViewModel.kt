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
                //image resouce if it's rainy or cloudy
                val img = if(data.weather.first().main.contains("rain",true)) Res.drawable.weather_clouds else Res.drawable.weather_precipitate

                //Day of week with 1st letter capital
                val text = Instant.fromEpochMilliseconds(data.dt).toLocalDateTime(TimeZone.currentSystemDefault()).dayOfWeek.name.substring(0,3).lowercase()
                val currDay = text.substring(0,1).uppercase()+text.substring(1,3)


                // temp in celsius
                val temp = (data.temp.max - 273.15).toInt().toString()+"° C"

                // gradient color change for today
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
        getSevenDayForeCast(getDummyDailyData())
    }


    @OptIn(ExperimentalTime::class)
    fun getDummyDailyData(): List<DailyData> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val oneDayInMillis = 24 * 60 * 60 * 1000L

        return listOf(
            DailyData(
                dt = currentTime, // Today
                temp = DailyTemp(min = 295.15, max = 308.15), // 22°C to 35°C
                weather = listOf(
                    WeatherDescription(
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d"
                    )
                ),
                summary = "Sunny and hot day with clear skies throughout"
            ),
            DailyData(
                dt = currentTime + oneDayInMillis, // Tomorrow
                temp = DailyTemp(min = 293.15, max = 305.15), // 20°C to 32°C
                weather = listOf(
                    WeatherDescription(
                        main = "Clouds",
                        description = "few clouds",
                        icon = "02d"
                    )
                ),
                summary = "Partly cloudy with mild temperatures"
            ),
            DailyData(
                dt = currentTime + (2 * oneDayInMillis), // Day 3
                temp = DailyTemp(min = 291.15, max = 302.15), // 18°C to 29°C
                weather = listOf(
                    WeatherDescription(
                        main = "Rain",
                        description = "light rain",
                        icon = "10d"
                    )
                ),
                summary = "Light rainfall expected with cooler temperatures"
            ),
            DailyData(
                dt = currentTime + (3 * oneDayInMillis), // Day 4
                temp = DailyTemp(min = 289.15, max = 298.15), // 16°C to 25°C
                weather = listOf(
                    WeatherDescription(
                        main = "Rain",
                        description = "moderate rain",
                        icon = "10d"
                    )
                ),
                summary = "Moderate rain with significantly cooler weather"
            ),
            DailyData(
                dt = currentTime + (4 * oneDayInMillis), // Day 5
                temp = DailyTemp(min = 290.15, max = 301.15), // 17°C to 28°C
                weather = listOf(
                    WeatherDescription(
                        main = "Clouds",
                        description = "broken clouds",
                        icon = "04d"
                    )
                ),
                summary = "Cloudy day with temperatures starting to rise"
            ),
            DailyData(
                dt = currentTime + (5 * oneDayInMillis), // Day 6
                temp = DailyTemp(min = 292.15, max = 306.15), // 19°C to 33°C
                weather = listOf(
                    WeatherDescription(
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d"
                    )
                ),
                summary = "Return to sunny conditions with warming temperatures"
            ),
            DailyData(
                dt = currentTime + (6 * oneDayInMillis), // Day 7
                temp = DailyTemp(min = 294.15, max = 309.15), // 21°C to 36°C
                weather = listOf(
                    WeatherDescription(
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d"
                    )
                ),
                summary = "Hot and sunny weather continues"
            ),
            DailyData(
                dt = currentTime + (7 * oneDayInMillis), // Day 8
                temp = DailyTemp(min = 296.15, max = 311.15), // 23°C to 38°C
                weather = listOf(
                    WeatherDescription(
                        main = "Clear",
                        description = "clear sky",
                        icon = "01d"
                    )
                ),
                summary = "Very hot day with intense sunshine"
            ),
            DailyData(
                dt = currentTime + (8 * oneDayInMillis), // Day 9
                temp = DailyTemp(min = 297.15, max = 310.15), // 24°C to 37°C
                weather = listOf(
                    WeatherDescription(
                        main = "Clouds",
                        description = "scattered clouds",
                        icon = "03d"
                    )
                ),
                summary = "Hot with some cloud cover providing occasional relief"
            )
        )
    }

// Usage example:
// val dailyForecast = getDummyDailyData()



}



data class SevenDayWeatherUiState(
    val sevenDayForeCast: List<DailyWeatherRowModel>
)