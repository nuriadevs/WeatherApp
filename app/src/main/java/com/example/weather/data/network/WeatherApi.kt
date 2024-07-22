package com.example.weather.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton
import com.example.weather.BuildConfig
import com.example.weather.data.model.Weather
import retrofit2.http.Path

@Singleton
interface WeatherApi {

    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val UNITS_METRIC = "metric"
        const val UNITS_IMPERIAL = "us"
    }

    @GET("VisualCrossingWebServices/rest/services/timeline/{location}")
    suspend fun getWeather(
        @Path("location") location: String,
        @Query("unitGroup") unitGroup: String?,
        @Query("key") apiKey: String = API_KEY

    ): Weather


}