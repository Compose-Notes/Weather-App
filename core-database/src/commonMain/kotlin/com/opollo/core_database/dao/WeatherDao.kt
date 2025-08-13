package com.opollo.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.opollo.core_database.model.WeatherResponseEntity

@Dao
interface WeatherDao {

    @Query("SELECT * FROM current_weather")
    suspend fun getCurrentWeather(): WeatherResponseEntity?

    @Upsert
    suspend fun insertWeatherData(weather: WeatherResponseEntity)

    @Query("DELETE FROM current_weather")
    suspend fun deleteWeatherData()

    @Query("SELECT lastUpdated FROM current_weather")
    suspend fun getLastUpdateTime():Long?
}