package com.example.weather.data.repository

import com.example.weather.data.database.WeatherDao
import com.example.weather.data.database.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val weatherDao: WeatherDao) {

    fun getUnits(): Flow<List<Unit>> = weatherDao.getUnits()
    suspend fun insertUnit(unitGroup: Unit) = weatherDao.insertUnit(unitGroup)
    suspend fun updateUnit(unitGroup: Unit) = weatherDao.updateUnit(unitGroup)
    suspend fun deleteAllUnits() = weatherDao.deleteAllUnits()
    suspend fun deleteUnit(unitGroup: Unit) = weatherDao.deleteUnit(unitGroup)

}