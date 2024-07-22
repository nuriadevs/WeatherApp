package com.example.weather.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.weather.data.database.WeatherDao
import com.example.weather.data.database.WeatherDatabase
import com.example.weather.data.network.WeatherApi
import com.example.weather.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {


    @Singleton
    @Provides
    fun provideWeatherDao(weatherDataBase: WeatherDatabase): WeatherDao =
        weatherDataBase.weatherDao()


    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            "weather_database"
        ).fallbackToDestructiveMigration().build()


            @Provides
            @Singleton
            fun provideOpenWeatherApi(): WeatherApi {
                return Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherApi::class.java)
            }


}