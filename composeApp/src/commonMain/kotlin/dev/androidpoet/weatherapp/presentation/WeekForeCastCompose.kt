package dev.androidpoet.weatherapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import weather_app.composeapp.generated.resources.Res
import weather_app.composeapp.generated.resources.icon_left
import weather_app.composeapp.generated.resources.icon_right
import weather_app.composeapp.generated.resources.weather_clouds
import weather_app.composeapp.generated.resources.weather_precipitate
import kotlin.collections.listOf
import kotlin.coroutines.coroutineContext
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
@Composable
fun DayForeCastCompose(modifier: Modifier,model: WeekForeCastComposeModel,onClick: () -> Unit){
    val currResource = if(model.weather.first().main.contains("rain",true)) Res.drawable.weather_clouds else Res.drawable.weather_precipitate
    val currDay = Instant.fromEpochMilliseconds(model.dt).toLocalDateTime(TimeZone.currentSystemDefault()).dayOfWeek.name.substring(0,3)
    val tempC = (model.temp.day - 273.15).toInt().toString()+"° C"

    val isToday = Instant.fromEpochMilliseconds(model.dt).toLocalDateTime(TimeZone.currentSystemDefault()) == Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    val linearGradient = if(isToday) listOf(Color(0xFF3E2D8F),Color(0xFF9D52AC)) else listOf(Color(0xFF3E2D8F),Color(0xFF8E78C8))
    Card (
       modifier = modifier.background(
           brush = Brush.verticalGradient(colors = linearGradient)
       ) ,
        shape = RoundedCornerShape(10.dp,10.dp,10.dp,10.dp)
    ) {
        Column {

            Text(text = tempC)
            Image(
                painter = painterResource(currResource),null
            )
            Text(text=currDay)


        }
    }
}


@Composable
fun DayForeCastRow(modifier: Modifier){
    Column (modifier=modifier.fillMaxWidth()){
        Text(text="7-Days Forecasts")
        Row {
            Image(painter = painterResource(Res.drawable.icon_left),null)
            LazyRow {  }
            Image(painterResource(Res.drawable.icon_right),null)
        }
    }
}


data class WeekForeCastComposeModel(
    val dt: Long,
    val sunrise: Long,
    val sunset: Long,
    val moonrise: Long,
    val moonset: Long,
    val moonPhase: Double,
    val summary: String,
    val temp: Temp,
    val feelsLike: FeelsLike,
    val pressure: Long,
    val humidity: Long,
    val dewPoint: Double,
    val windSpeed: Double,
    val windDeg: Long,
    val windGust: Double,
    val weather: List<Weather>,
    val clouds: Long,
    val pop: Double,
    val rain: Double,
    val uvi: Double,
)

data class Temp(
    val day: Double,
    val min: Double,
    val max: Double,
    val night: Double,
    val eve: Double,
    val morn: Double,
)

data class FeelsLike(
    val day: Double,
    val night: Double,
    val eve: Double,
    val morn: Double,
)

data class Weather(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)