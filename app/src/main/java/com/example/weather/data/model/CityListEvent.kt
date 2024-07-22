package com.example.weather.data.model

/**
 * A sealed class representing different types of recipe list events.
 */
sealed class CityListEvent {
    data class Search(val searchString: String) : CityListEvent()
}