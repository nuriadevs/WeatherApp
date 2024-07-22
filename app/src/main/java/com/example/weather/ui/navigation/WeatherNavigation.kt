package com.example.weather.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.weather.data.model.Day
import com.example.weather.data.model.Weather
import com.example.weather.data.network.WeatherApi
import com.example.weather.ui.components.CardWeatherDaily
import com.example.weather.ui.screens.about.WeatherAboutScreen
import com.example.weather.ui.screens.home.HomeViewModel
import com.example.weather.ui.screens.splash.WeatherSplashScreen
import com.example.weather.ui.screens.home.WeatherHomeScreen
import com.example.weather.ui.screens.details.WeatherDetailsScreen
import com.example.weather.ui.screens.settings.SettingsViewModel
import com.example.weather.ui.screens.settings.WeatherSettingsScreen
import com.google.gson.Gson


@Composable
fun WeatherNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = WeatherScreens.WeatherSplashScreen.name
    ) {

        composable(WeatherScreens.WeatherSplashScreen.name) {
            WeatherSplashScreen(navController = navController)
        }

        val route = WeatherScreens.WeatherHomeScreen.name
        composable("$route/{city}", arguments = listOf(navArgument(name = "city") {
            type = NavType.StringType
        })) { navBack ->
            navBack.arguments?.getString("city").let { city ->

                val homeViewModel = hiltViewModel<HomeViewModel>()
                WeatherHomeScreen(
                    navController = navController,
                    homeViewModel = homeViewModel,
                    initialCity = city,

                )
            }
        }

        val routeDetails = "${WeatherScreens.WeatherDetailsScreen.name}/{city}?weatherJson={weatherJson}&unit={unit}"

        composable(
            route = routeDetails,
            arguments = listOf(
                navArgument("unit") { type = NavType.StringType }, // Añadir parámetro unit

                navArgument("city") { type = NavType.StringType },
                navArgument("weatherJson") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val unit = backStackEntry.arguments?.getString("unit") ?: WeatherApi.UNITS_METRIC // Valor por defecto

            val city = backStackEntry.arguments?.getString("city")
            val weatherJson = backStackEntry.arguments?.getString("weatherJson")
            val weather = Gson().fromJson(weatherJson, Day::class.java)
            WeatherDetailsScreen(city = city, day = weather, navController = navController, unit = unit)
        }

        composable(WeatherScreens.WeatherAboutScreen.name) {
            WeatherAboutScreen(navController = navController)
        }


        composable(WeatherScreens.WeatherSettingsScreen.name) {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()
            WeatherSettingsScreen(navController = navController, settingsViewModel = settingsViewModel)

        }


    }
}

