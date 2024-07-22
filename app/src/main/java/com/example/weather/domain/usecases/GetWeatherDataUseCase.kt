package com.example.weather.domain.usecases



import android.util.Log
import com.example.weather.data.model.Weather
import com.example.weather.data.repository.WeatherRepository
import com.example.weather.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOError
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * A use case class for fetching food recipes.
 */
class GetWeatherDataUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    fun execute(searchString: String, unitGroup: String): Flow<Resource<Weather>> = flow {
        try {
            emit(Resource.Loading())
            val cityListResult = weatherRepository.getWeather(searchString, unitGroup)
            cityListResult.data?.let { weatherData ->
                emit(Resource.Success(weatherData))
                Log.d("Caso de uso", "UseCase: ${weatherData.currentConditions.temp}")
            } ?: run {
                emit(Resource.Error("No weather found"))
            }
        } catch (e: IOError) {
            emit(Resource.Error("No internet connection"))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: "Error"))
        } catch (e: SocketTimeoutException) {
            emit(Resource.Error("Timeout"))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }
}