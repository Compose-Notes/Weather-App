package com.opollo.core_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.opollo.core_database.dao.WeatherDao
import com.opollo.core_database.model.WeatherResponseEntity
import com.opollo.core_database.model.WeatherTypeConverters

@Database(
    entities = [WeatherResponseEntity::class],
    version = 1
)
@TypeConverters(WeatherTypeConverters::class)
abstract class WeatherDatabase: RoomDatabase() {
    abstract fun weatherDao(): WeatherDao

    companion object{
        const val DATABASE_NAME ="Weather_Database"
    }

}