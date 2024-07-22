package com.example.weather.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.weather.data.database.Unit


@Database(entities = [Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase: RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

}