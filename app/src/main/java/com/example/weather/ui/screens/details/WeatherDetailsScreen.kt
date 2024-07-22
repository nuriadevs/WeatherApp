package com.example.weather.ui.screens.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.weather.R
import com.example.weather.data.model.Day
import com.example.weather.data.model.Hour
import com.example.weather.data.network.WeatherApi
import com.example.weather.ui.components.WeatherAppBar
import com.example.weather.utils.Constants.BASE_IMAGE_URL
import com.example.weather.utils.WeatherStateImage
import com.example.weather.utils.formDataComplete


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherDetailsScreen(
    city: String?,
    day: Day,
    navController: NavController,
    unit: String
) {

    val imageUrl = "$BASE_IMAGE_URL${day.icon}.svg"

    val temperatureUnit = if (unit == WeatherApi.UNITS_IMPERIAL) "°F" else "°C"


    Scaffold(modifier = Modifier.padding(top = 16.dp),
        topBar = {
            WeatherAppBar(
                title = city ?: "",
                navController = navController,
                icon = Icons.Default.ArrowBack,
                isIconLocation = true,
                isMainScreen = false
            ) {
                navController.popBackStack()
            }
        }) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    elevation = CardDefaults.elevatedCardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = formDataComplete(day.datetimeEpoch),
                                style = MaterialTheme.typography.headlineSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.secondary
                                )

                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "${day.temp.toInt()}$temperatureUnit",
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        )

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {

                            WeatherStateImage(
                                imageUrl,
                                modifier = Modifier.padding(10.dp),
                                contentDescription = stringResource(id = R.string.description_weeather_image),
                                contentScale = ContentScale.Crop,
                                size = 60
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = day.description,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Normal,
                                color = MaterialTheme.colorScheme.secondary
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.sunrise),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = stringResource(id = R.string.icon), modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = day.sunrise,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )

                            }

                            Row(
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.Bottom
                            ) {
                                Icon(
                                    painterResource(id = R.drawable.sunset),
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = stringResource(id = R.string.icon), modifier = Modifier.size(30.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = day.sunset,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Normal,
                                        color = MaterialTheme.colorScheme.secondary
                                    )
                                )

                            }

                        }

                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                WeatherHourList()
                Spacer(modifier = Modifier.height(12.dp))
                WeatherHourList(list = day.hours, unit = unit)

            }
        }
    }
}

@Composable
fun WeatherHourList() {

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = R.string.houtly_forecast),
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary
            )

        )


    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherHourList(
    unit: String,
    list: List<Hour>
) {
    ElevatedCard() {
        LazyColumn {
            items(list) { hour ->
                WeatherHourItem(item = hour, unit = unit)
            }
        }

    }
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun WeatherHourItem(
    unit: String,
    modifier: Modifier = Modifier,
    item: Hour,
    onClickExpandedItem: (Hour) -> Unit = {},
) {
    var isExpanded by remember { mutableStateOf(false) }
    val transition = updateTransition(targetState = isExpanded, label = "")
    val temperatureUnit = if (unit == WeatherApi.UNITS_IMPERIAL) "°F" else "°C"


    Column(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        Row(
            modifier = Modifier
                .height(70.dp)
                .fillMaxWidth()
                .clickable {
                    isExpanded = !isExpanded
                    onClickExpandedItem(item)
                }
                .padding(horizontal = 10.dp, vertical = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                Text(
                    text = (item.datetime).take(5),
                    modifier = Modifier.padding(bottom = 5.dp),
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.secondary),
                )

                Text(
                    text = item.conditions,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary),
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                val imageUrl = "$BASE_IMAGE_URL${item.icon}.svg"

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {

                    WeatherStateImage(
                        imageUrl, modifier = Modifier.padding(10.dp),
                        contentDescription = stringResource(id = R.string.description_weeather_image),
                        contentScale = ContentScale.Crop,
                        size = 20
                    )

                    Text(
                        text = "${item.temp.toInt()}$temperatureUnit",
                        style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary),
                    )
                }

                transition.AnimatedContent(transitionSpec = {
                    if (!targetState) {
                        slideInVertically { height -> height } + fadeIn() togetherWith slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        slideInVertically { height -> -height } + fadeIn() togetherWith slideOutVertically { height -> height } + fadeOut()
                    }.using(
                        SizeTransform(clip = false)
                    )
                }) { state ->
                    Icon(
                        imageVector = if (!state) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = stringResource(id = R.string.icon),
                        modifier = Modifier.padding(start = 10.dp),
                    )
                }
            }
        }

        transition.AnimatedVisibility(
            visible = { it },
            enter = expandVertically(),
            exit = shrinkVertically(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 5.dp),
            ) {
                WeatherInformation(
                    title = stringResource(id = R.string.wind),
                    description = "${item.windspeed}" + stringResource(id = R.string.kmH)
                )

                WeatherInformation(
                    title = stringResource(id = R.string.pressure),
                    description = "${item.pressure}" + stringResource(id = R.string.mb)
                )

                WeatherInformation(
                    title = stringResource(id = R.string.uvIndex),
                    description = item.uvindex.toString()
                )

                WeatherInformation(
                    title = stringResource(id = R.string.visibility),
                    description = "${item.visibility}" + stringResource(id = R.string.km)
                )

                WeatherInformation(
                    title = stringResource(id = R.string.humidity),
                    description = "${item.humidity}" + stringResource(id = R.string.percentage)
                )

                WeatherInformation(
                    title = stringResource(id = R.string.solarRadiation),
                    description = "${item.solarradiation}" + stringResource(id = R.string.Wm2)
                )

                WeatherInformation(
                    title = stringResource(id = R.string.solarEnergy),
                    description = "${item.solarenergy} " + stringResource(id = R.string.Wm2)
                )

            }
        }

        HorizontalDivider(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.inversePrimary
        )
    }
}

@Composable
fun WeatherInformation(
    modifier: Modifier = Modifier,
    title: String = "",
    description: String = "",
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically

    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary)
        )

        Text(
            text = description,
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.primary),
        )
    }
}

