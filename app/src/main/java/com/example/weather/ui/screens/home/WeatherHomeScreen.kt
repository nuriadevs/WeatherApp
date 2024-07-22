package com.example.weather.ui.screens.home

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.ui.components.WeatherAppBar
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.data.model.CityListEvent
import com.example.weather.data.model.Day
import com.example.weather.data.model.Weather
import com.example.weather.data.network.WeatherApi
import com.example.weather.ui.components.CardWeather
import com.example.weather.ui.components.CardWeatherDaily
import com.example.weather.ui.components.WeatherearchBar
import com.example.weather.ui.navigation.WeatherScreens
import com.example.weather.ui.screens.settings.SettingsViewModel
import com.google.gson.Gson


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherHomeScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    initialCity: String?
) {

    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    val unit =
        if (unitFromDb.isNullOrEmpty()) WeatherApi.UNITS_IMPERIAL else unitFromDb[0].unitGroup
    homeViewModel.setUnit(unit)

    var city by rememberSaveable { mutableStateOf(initialCity ?: "") }



    LaunchedEffect(city) {
        if (city.isNotEmpty()) {
            homeViewModel.onEvent(CityListEvent.Search(city))
        }
    }


    MainScaffold(
        homeViewModel = homeViewModel,
        navController = navController,
        onCitySelected = { newCity ->
            city = newCity
            homeViewModel.onEvent(CityListEvent.Search(newCity))
        },
        city = city,
        unit = unit

    )
}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScaffold(
    city: String,
    homeViewModel: HomeViewModel = hiltViewModel(),
    navController: NavController,
    onCitySelected: (String) -> Unit,
    unit: String
) {
    val searchQueryState = remember { mutableStateOf("") }

    val state = homeViewModel.state.value

    Scaffold(modifier = Modifier.padding(top = 16.dp),

        topBar = {
            WeatherAppBar(
                isMainScreen = true,
                isIconLocation = true,
                title = state.weather?.address.toString(),
                navController = navController,
                isLoading = state.isLoading

            )
        },
        content = {
            Surface(
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    WeatherearchBar(
                        placeholder = stringResource(id = R.string.searchCity),
                        query = searchQueryState.value,
                        onQueryChange = { newQuery ->
                            searchQueryState.value = newQuery
                        },
                        onCitySelected = onCitySelected,
                        searchQueryState = searchQueryState
                    )

                    Spacer(modifier = Modifier.height(10.dp))


                    when {
                        state.isLoading -> {
                            CircularProgressIndicator()
                        }

                        state.weather != null -> {
                            MainContent(
                                city = city,
                                navController = navController,
                                homeViewModel = homeViewModel,
                                unit = unit
                            )
                        }

                        state.error.isNotEmpty() -> {
                            Text(stringResource(id = R.string.failedLoad) + " ${state.error}")
                        }
                    }


                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainContent(
    city: String,
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    unit: String
) {

    val state = homeViewModel.state.value
    when {
        state.isLoading -> {
            CircularProgressIndicator()
        }

        state.weather != null -> {

            Column {
                CardWeather(data = state.weather, unit = unit)
                Spacer(modifier = Modifier.height(16.dp))
                NextDays(
                    navController = navController,
                    city = city,
                    data = state.weather,
                    days = state.weather.days,
                    unit = unit
                )
            }
        }

        state.error.isNotEmpty() -> {
            Text(stringResource(id = R.string.failedLoad) + " ${state.error}")
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NextDays(
    unit: String,
    city: String,
    navController: NavController,
    data: Weather,
    days: List<Day>,
    mainViewModel: HomeViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.nextDays),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )
        )

        Spacer(modifier = Modifier.height(6.dp))
        val state = mainViewModel.state.value
        if (state.isLoading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            val gson = Gson()
            LazyRow(
                contentPadding = PaddingValues(1.dp)
            ) {
                items(state.weather?.days ?: emptyList()) { day ->

                    CardWeatherDaily(day, onItemClick = {
                        val dayJson = gson.toJson(it)
                        navController.navigate("${WeatherScreens.WeatherDetailsScreen.name}/$city?weatherJson=$dayJson&unit=$unit")
                    }, unit)

                }

            }
        }


    }
}


