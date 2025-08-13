package com.opollo.core_database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.serialization.json.Json
import kotlin.random.Random
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


@Entity(tableName = "current_weather")
@TypeConverters(WeatherTypeConverters::class)
data class WeatherResponseEntity @OptIn(ExperimentalTime::class, ExperimentalUuidApi::class) constructor(
    @PrimaryKey
    val id: String = Uuid.random().toString(),
    val timezone: String,
    val timezoneOffset:String,
    val current:String,
    val hourly:String,
    val daily: String,
    val lastUpdated:Long = Clock.System.now().toEpochMilliseconds()

)

class WeatherTypeConverters{
    val json = Json { ignoreUnknownKeys = true }

    @TypeConverter
    fun fromCurrentWeather(value:List<*>):String{
        return json.encodeToString(value)
    }
    @TypeConverter
    fun toCurrentWeather(value:String):List<*>{
        return json.decodeFromString(value)
    }

    @TypeConverter
    fun fromHourlyList(value:List<*>):String{
        return json.encodeToString(value)
    }

    @TypeConverter
    fun toHourlyList(value:String):List<*>{
        return json.decodeFromString(value)
    }
    @TypeConverter
    fun fromDailyList(value:List<*>):String{
        return json.encodeToString(value)
    }
    @TypeConverter
    fun toDailyList(value:String):List<*>{
        return json.decodeFromString(value)
    }

}

