package com.opollo.core_database.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<WeatherDatabase> {
    val dbFile = context.getDatabasePath(WeatherDatabase.DATABASE_NAME)
    return Room.databaseBuilder<WeatherDatabase>(
        context = context,
        name = dbFile.absolutePath
    ).setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)

}