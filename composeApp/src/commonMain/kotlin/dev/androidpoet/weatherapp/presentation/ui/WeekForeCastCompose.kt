package dev.androidpoet.weatherapp.presentation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.androidpoet.weatherapp.data.remote.FeelsLike
import dev.androidpoet.weatherapp.data.remote.Temp
import dev.androidpoet.weatherapp.data.remote.Weather
import dev.androidpoet.weatherapp.data.remote.WeekForeCastComposeModel
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import weather_app.composeapp.generated.resources.Res
import weather_app.composeapp.generated.resources.icon_left
import weather_app.composeapp.generated.resources.icon_right
import weather_app.composeapp.generated.resources.weather_clouds
import weather_app.composeapp.generated.resources.weather_precipitate
import kotlin.collections.listOf
import kotlin.math.max
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant


@OptIn(ExperimentalTime::class)
@Composable
fun DayForeCastCompose(modifier: Modifier,model: WeekForeCastComposeModel,onClick: () -> Unit){
    val currResource = if(model.weather.first().main.contains("rain",true)) Res.drawable.weather_clouds else Res.drawable.weather_precipitate
    val text = Instant.fromEpochMilliseconds(model.dt).toLocalDateTime(TimeZone.currentSystemDefault()).dayOfWeek.name.substring(0,3).lowercase()
    val currDay = text.substring(0,1).uppercase()+text.substring(1,3)
    val tempC = (model.temp.day - 273.15).toInt().toString()+"° C"
    println("DEBUG: The dt value being used is ${model.dt}")

    val isToday = Instant.fromEpochMilliseconds(model.dt).toLocalDateTime(TimeZone.currentSystemDefault()).date == Clock.System.todayIn(TimeZone.currentSystemDefault())


    val linearGradient = if(isToday) listOf(Color(0xFF3E2D8F),Color(0xFF9D52AC)) else listOf(Color(0xFF3E2D8F),Color(0xFF8E78C8))
    Card (
       modifier = modifier ,
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        ),
        shape = RoundedCornerShape(40.dp,40.dp,40.dp,40.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.background(
            brush = Brush.verticalGradient(colors = linearGradient)
        )) {


            Column (horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(top=10.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)){

                Text(text = tempC, modifier = Modifier.padding(5.dp).background(Color.Transparent),color = Color.White)
                Image(
                    painter = painterResource(currResource),null,modifier=Modifier.size(36.dp).background(Color.Transparent)
                )
                Text(text=currDay, modifier = Modifier.padding(5.dp).background(Color.Transparent),color = Color.White)
            }

        }
    }
}


@Composable
fun DayForeCastRow(modifier: Modifier,data: List<WeekForeCastComposeModel>){
    Column (modifier=modifier.fillMaxWidth().padding(15.dp)){
        Text(text="7-Days Forecasts", color =   Color.White, fontWeight = FontWeight.SemiBold,modifier= Modifier.padding(start=28.dp,top=16.dp))
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        Spacer(modifier=Modifier.size(10.dp))
        Row (verticalAlignment =Alignment.CenterVertically, modifier = modifier.fillMaxWidth()){
            // have used animate scroll to avoid flickering for button scrolling
            Image(painter = painterResource(Res.drawable.icon_left),null,modifier = Modifier.size(24.dp).clickable(indication = null, interactionSource = null,onClick = {
                scope.launch {
                    listState.animateScrollToItem(max(0, listState.firstVisibleItemIndex-1))
                }
            }))
            LazyRow (state = listState,modifier = Modifier.padding(2.dp).weight(1f)){
                items(data,key = {it.dt}){
                    Spacer(modifier.size(2.dp))
                    DayForeCastCompose(Modifier,it){
                    }
                    Spacer(modifier.size(2.dp))

                }
            }
            Image(painterResource(Res.drawable.icon_right),null,modifier = Modifier.size(24.dp).clickable(indication = null, interactionSource = null, onClick = {
                scope.launch {
                    listState.animateScrollToItem(listState.firstVisibleItemIndex+1)
                }
            }))
        }
    }
}


@Preview
@Composable
fun DayForeCastRowPreview(){


    Column(modifier = Modifier.fillMaxSize()) {
        DayForeCastRow(modifier = Modifier,data = getDummyWeatherForecast())
    }
}


@OptIn(ExperimentalTime::class)
fun getDummyWeatherForecast(): List<WeekForeCastComposeModel> {
    val currentTimeMillis = Clock.System.now().toEpochMilliseconds()
    val oneDayMillis = 24 * 60 * 60 * 1000L

    return listOf(
        // Day 1 - Today
        WeekForeCastComposeModel(
            dt = currentTimeMillis,
            sunrise = currentTimeMillis + (6 * 60 * 60 * 1000), // 6 AM
            sunset = currentTimeMillis + (18 * 60 * 60 * 1000), // 6 PM
            moonrise = currentTimeMillis + (20 * 60 * 60 * 1000), // 8 PM
            moonset = currentTimeMillis + (5 * 60 * 60 * 1000), // 5 AM
            moonPhase = 0.16,
            summary = "Expect a day of partly cloudy with light rain",
            temp = Temp(
                day = 299.03, // 26°C
                min = 290.69, // 17°C
                max = 300.35, // 27°C
                night = 291.45, // 18°C
                eve = 297.51, // 24°C
                morn = 292.55  // 19°C
            ),
            feelsLike = FeelsLike(
                day = 299.21,
                night = 291.37,
                eve = 297.86,
                morn = 292.87
            ),
            pressure = 1016,
            humidity = 59,
            dewPoint = 290.48,
            windSpeed = 3.98,
            windDeg = 76,
            windGust = 8.92,
            weather = listOf(
                Weather(
                    id = 500,
                    main = "Rain",
                    description = "light rain",
                    icon = "10d"
                )
            ),
            clouds = 92,
            pop = 0.47,
            rain = 0.15,
            uvi = 9.23
        ),

        // Day 2
        WeekForeCastComposeModel(
            dt = currentTimeMillis + oneDayMillis,
            sunrise = currentTimeMillis + oneDayMillis + (6 * 60 * 60 * 1000),
            sunset = currentTimeMillis + oneDayMillis + (18 * 60 * 60 * 1000),
            moonrise = currentTimeMillis + oneDayMillis + (21 * 60 * 60 * 1000),
            moonset = currentTimeMillis + oneDayMillis + (6 * 60 * 60 * 1000),
            moonPhase = 0.25,
            summary = "Sunny day with few clouds",
            temp = Temp(
                day = 302.15, // 29°C
                min = 292.15, // 19°C
                max = 304.15, // 31°C
                night = 294.15, // 21°C
                eve = 300.15, // 27°C
                morn = 295.15  // 22°C
            ),
            feelsLike = FeelsLike(
                day = 303.15,
                night = 295.15,
                eve = 301.15,
                morn = 296.15
            ),
            pressure = 1018,
            humidity = 45,
            dewPoint = 287.15,
            windSpeed = 2.5,
            windDeg = 120,
            windGust = 5.2,
            weather = listOf(
                Weather(
                    id = 801,
                    main = "Clouds",
                    description = "few clouds",
                    icon = "02d"
                )
            ),
            clouds = 20,
            pop = 0.1,
            rain = 0.0,
            uvi = 11.5
        ),

        // Day 3
        WeekForeCastComposeModel(
            dt = currentTimeMillis + (2 * oneDayMillis),
            sunrise = currentTimeMillis + (2 * oneDayMillis) + (6 * 60 * 60 * 1000),
            sunset = currentTimeMillis + (2 * oneDayMillis) + (18 * 60 * 60 * 1000),
            moonrise = currentTimeMillis + (2 * oneDayMillis) + (22 * 60 * 60 * 1000),
            moonset = currentTimeMillis + (2 * oneDayMillis) + (7 * 60 * 60 * 1000),
            moonPhase = 0.35,
            summary = "Clear sky with bright sunshine",
            temp = Temp(
                day = 305.15, // 32°C
                min = 295.15, // 22°C
                max = 307.15, // 34°C
                night = 297.15, // 24°C
                eve = 303.15, // 30°C
                morn = 298.15  // 25°C
            ),
            feelsLike = FeelsLike(
                day = 308.15,
                night = 299.15,
                eve = 306.15,
                morn = 300.15
            ),
            pressure = 1020,
            humidity = 35,
            dewPoint = 285.15,
            windSpeed = 1.8,
            windDeg = 95,
            windGust = 3.2,
            weather = listOf(
                Weather(
                    id = 800,
                    main = "Clear",
                    description = "clear sky",
                    icon = "01d"
                )
            ),
            clouds = 5,
            pop = 0.0,
            rain = 0.0,
            uvi = 12.8
        ),

        // Day 4
        WeekForeCastComposeModel(
            dt = currentTimeMillis + (3 * oneDayMillis),
            sunrise = currentTimeMillis + (3 * oneDayMillis) + (6 * 60 * 60 * 1000),
            sunset = currentTimeMillis + (3 * oneDayMillis) + (18 * 60 * 60 * 1000),
            moonrise = currentTimeMillis + (3 * oneDayMillis) + (23 * 60 * 60 * 1000),
            moonset = currentTimeMillis + (3 * oneDayMillis) + (8 * 60 * 60 * 1000),
            moonPhase = 0.45,
            summary = "Thunderstorm with heavy rain expected",
            temp = Temp(
                day = 296.15, // 23°C
                min = 288.15, // 15°C
                max = 298.15, // 25°C
                night = 290.15, // 17°C
                eve = 294.15, // 21°C
                morn = 291.15  // 18°C
            ),
            feelsLike = FeelsLike(
                day = 297.15,
                night = 291.15,
                eve = 295.15,
                morn = 292.15
            ),
            pressure = 1010,
            humidity = 85,
            dewPoint = 293.15,
            windSpeed = 8.5,
            windDeg = 240,
            windGust = 15.2,
            weather = listOf(
                Weather(
                    id = 200,
                    main = "Thunderstorm",
                    description = "thunderstorm with light rain",
                    icon = "11d"
                )
            ),
            clouds = 95,
            pop = 0.85,
            rain = 8.5,
            uvi = 6.2
        ),

        // Day 5
        WeekForeCastComposeModel(
            dt = currentTimeMillis + (4 * oneDayMillis),
            sunrise = currentTimeMillis + (4 * oneDayMillis) + (6 * 60 * 60 * 1000),
            sunset = currentTimeMillis + (4 * oneDayMillis) + (18 * 60 * 60 * 1000),
            moonrise = currentTimeMillis + (4 * oneDayMillis) + (23 * 60 * 60 * 1000) + (30 * 60 * 1000),
            moonset = currentTimeMillis + (4 * oneDayMillis) + (9 * 60 * 60 * 1000),
            moonPhase = 0.5,
            summary = "Overcast with light drizzle",
            temp = Temp(
                day = 293.15, // 20°C
                min = 286.15, // 13°C
                max = 295.15, // 22°C
                night = 288.15, // 15°C
                eve = 291.15, // 18°C
                morn = 289.15  // 16°C
            ),
            feelsLike = FeelsLike(
                day = 294.15,
                night = 289.15,
                eve = 292.15,
                morn = 290.15
            ),
            pressure = 1012,
            humidity = 75,
            dewPoint = 289.15,
            windSpeed = 4.2,
            windDeg = 180,
            windGust = 7.8,
            weather = listOf(
                Weather(
                    id = 300,
                    main = "Drizzle",
                    description = "light intensity drizzle",
                    icon = "09d"
                )
            ),
            clouds = 85,
            pop = 0.65,
            rain = 2.3,
            uvi = 4.5
        ),

        // Day 6
        WeekForeCastComposeModel(
            dt = currentTimeMillis + (5 * oneDayMillis),
            sunrise = currentTimeMillis + (5 * oneDayMillis) + (6 * 60 * 60 * 1000),
            sunset = currentTimeMillis + (5 * oneDayMillis) + (18 * 60 * 60 * 1000),
            moonrise = currentTimeMillis + (5 * oneDayMillis) + (24 * 60 * 60 * 1000),
            moonset = currentTimeMillis + (5 * oneDayMillis) + (10 * 60 * 60 * 1000),
            moonPhase = 0.6,
            summary = "Partly cloudy becoming sunny",
            temp = Temp(
                day = 298.15, // 25°C
                min = 290.15, // 17°C
                max = 300.15, // 27°C
                night = 292.15, // 19°C
                eve = 296.15, // 23°C
                morn = 293.15  // 20°C
            ),
            feelsLike = FeelsLike(
                day = 299.15,
                night = 293.15,
                eve = 297.15,
                morn = 294.15
            ),
            pressure = 1015,
            humidity = 55,
            dewPoint = 286.15,
            windSpeed = 3.2,
            windDeg = 45,
            windGust = 6.1,
            weather = listOf(
                Weather(
                    id = 802,
                    main = "Clouds",
                    description = "scattered clouds",
                    icon = "03d"
                )
            ),
            clouds = 40,
            pop = 0.2,
            rain = 0.0,
            uvi = 8.9
        ),

        // Day 7
        WeekForeCastComposeModel(
            dt = currentTimeMillis + (6 * oneDayMillis),
            sunrise = currentTimeMillis + (6 * oneDayMillis) + (6 * 60 * 60 * 1000),
            sunset = currentTimeMillis + (6 * oneDayMillis) + (18 * 60 * 60 * 1000),
            moonrise = currentTimeMillis + (6 * oneDayMillis) + (60 * 60 * 1000),
            moonset = currentTimeMillis + (6 * oneDayMillis) + (11 * 60 * 60 * 1000),
            moonPhase = 0.7,
            summary = "Hot and humid with chance of afternoon showers",
            temp = Temp(
                day = 306.15, // 33°C
                min = 297.15, // 24°C
                max = 308.15, // 35°C
                night = 299.15, // 26°C
                eve = 304.15, // 31°C
                morn = 300.15  // 27°C
            ),
            feelsLike = FeelsLike(
                day = 310.15,
                night = 302.15,
                eve = 308.15,
                morn = 303.15
            ),
            pressure = 1008,
            humidity = 70,
            dewPoint = 295.15,
            windSpeed = 2.1,
            windDeg = 200,
            windGust = 4.5,
            weather = listOf(
                Weather(
                    id = 501,
                    main = "Rain",
                    description = "moderate rain",
                    icon = "10d"
                )
            ),
            clouds = 60,
            pop = 0.6,
            rain = 3.8,
            uvi = 10.2
        ),

        // Day 8
        WeekForeCastComposeModel(
            dt = currentTimeMillis + (7 * oneDayMillis),
            sunrise = currentTimeMillis + (7 * oneDayMillis) + (6 * 60 * 60 * 1000),
            sunset = currentTimeMillis + (7 * oneDayMillis) + (18 * 60 * 60 * 1000),
            moonrise = currentTimeMillis + (7 * oneDayMillis) + (2 * 60 * 60 * 1000),
            moonset = currentTimeMillis + (7 * oneDayMillis) + (12 * 60 * 60 * 1000),
            moonPhase = 0.8,
            summary = "Windy day with broken clouds",
            temp = Temp(
                day = 301.15, // 28°C
                min = 293.15, // 20°C
                max = 303.15, // 30°C
                night = 295.15, // 22°C
                eve = 299.15, // 26°C
                morn = 296.15  // 23°C
            ),
            feelsLike = FeelsLike(
                day = 303.15,
                night = 297.15,
                eve = 301.15,
                morn = 298.15
            ),
            pressure = 1014,
            humidity = 50,
            dewPoint = 283.15,
            windSpeed = 6.8,
            windDeg = 310,
            windGust = 12.5,
            weather = listOf(
                Weather(
                    id = 803,
                    main = "Clouds",
                    description = "broken clouds",
                    icon = "04d"
                )
            ),
            clouds = 75,
            pop = 0.3,
            rain = 0.5,
            uvi = 7.8
        ),

        // Day 9
        WeekForeCastComposeModel(
            dt = currentTimeMillis + (8 * oneDayMillis),
            sunrise = currentTimeMillis + (8 * oneDayMillis) + (6 * 60 * 60 * 1000),
            sunset = currentTimeMillis + (8 * oneDayMillis) + (18 * 60 * 60 * 1000),
            moonrise = currentTimeMillis + (8 * oneDayMillis) + (3 * 60 * 60 * 1000),
            moonset = currentTimeMillis + (8 * oneDayMillis) + (13 * 60 * 60 * 1000),
            moonPhase = 0.9,
            summary = "Pleasant day with gentle breeze",
            temp = Temp(
                day = 297.15, // 24°C
                min = 289.15, // 16°C
                max = 299.15, // 26°C
                night = 291.15, // 18°C
                eve = 295.15, // 22°C
                morn = 292.15  // 19°C
            ),
            feelsLike = FeelsLike(
                day = 298.15,
                night = 292.15,
                eve = 296.15,
                morn = 293.15
            ),
            pressure = 1017,
            humidity = 60,
            dewPoint = 285.15,
            windSpeed = 3.5,
            windDeg = 80,
            windGust = 6.8,
            weather = listOf(
                Weather(
                    id = 801,
                    main = "Clouds",
                    description = "few clouds",
                    icon = "02d"
                )
            ),
            clouds = 25,
            pop = 0.15,
            rain = 0.0,
            uvi = 9.5
        )

    )


}
