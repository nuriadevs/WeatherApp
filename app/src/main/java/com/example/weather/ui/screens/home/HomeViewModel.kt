package com.example.weather.ui.screens.home


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.model.CityListEvent
import com.example.weather.data.model.CityListState
import com.example.weather.data.network.WeatherApi
import com.example.weather.domain.usecases.GetWeatherDataUseCase
import com.example.weather.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: GetWeatherDataUseCase) : ViewModel() {

    private val _state = mutableStateOf(CityListState())
    val state: State<CityListState> = _state

    private var currentUnit: String = WeatherApi.UNITS_IMPERIAL // Default unit

    private var currentSearchString: String = ""
    private var job: Job? = null

    fun setUnit(unit: String) {
        currentUnit = unit
    }

    fun getCityData(searchString: String) {
        currentSearchString = searchString
        job?.cancel()

        job = repository.execute(searchString, currentUnit).onEach {
            when (it) {
                is Resource.Success -> {
                    _state.value = CityListState(weather = it.data)
                }
                is Resource.Error -> {
                    _state.value = CityListState(error = it.message ?: "Error")
                }
                is Resource.Loading -> {
                    _state.value = CityListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun onEvent(event: CityListEvent) {
        when (event) {
            is CityListEvent.Search -> {
                if (event.searchString.isNotBlank()) {
                    getCityData(event.searchString)
                }
            }
        }
    }

}