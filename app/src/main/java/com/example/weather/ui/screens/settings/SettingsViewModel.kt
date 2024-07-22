package com.example.weather.ui.screens.settings


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weather.data.database.Unit
import com.example.weather.data.repository.WeatherDbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDbRepository) :
    ViewModel() {

    private val _unitList = MutableStateFlow<List<Unit>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getUnits().distinctUntilChanged().collect { listOfUnits ->
                if (listOfUnits.isNullOrEmpty()) {

                } else {

                    _unitList.value = listOfUnits
                }

            }
        }
    }


    fun insertUnit(unitGroup: Unit) = viewModelScope.launch { repository.insertUnit(unitGroup) }
    fun updateUnit(unitGroup: Unit) = viewModelScope.launch { repository.updateUnit(unitGroup) }
    fun deleteUnit(unitGroup: Unit) = viewModelScope.launch { repository.deleteUnit(unitGroup) }
    fun deleteAllUnits() = viewModelScope.launch { repository.deleteAllUnits() }


}