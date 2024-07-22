package com.example.weather.data.model


/**
 * A data class representing the state of a recipe list.
 */
data class CityListState(
    val isLoading: Boolean = false,
    val weather: Weather? = null,
    val error: String = "",
    val searchString: String = "flan"
)