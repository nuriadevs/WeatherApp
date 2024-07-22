package com.example.weather.data.repository

import android.util.Log
import com.example.weather.core.exception.DataOrException
import com.example.weather.data.model.Weather
import com.example.weather.data.network.WeatherApi
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val api: WeatherApi) {

    suspend fun getWeather(cityQuery: String, unitGroup: String?): DataOrException<Weather, Boolean, Exception> {
        return try {
            val response = api.getWeather(location = cityQuery, unitGroup = unitGroup)
            DataOrException(data = response)
        } catch (e: Exception) {
            DataOrException(e = e)
        }
    }
}