package com.example.weather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.data.model.Day
import com.example.weather.utils.Constants.BASE_IMAGE_URL
import com.example.weather.utils.WeatherStateImage
import com.example.weather.utils.formDataComplete


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CardWeatherDaily(weather: Day, onItemClick: (Day) -> Unit, unit: String ) {

    val imageUrl = "${BASE_IMAGE_URL}${weather.icon}.svg"

    val temperatureUnit = if (unit == "us") "°F" else "°C"

    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .clickable {
                onItemClick(weather)
            },
        elevation = CardDefaults.elevatedCardElevation(5.dp),
        shape = MaterialTheme.shapes.medium,


        ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = formDataComplete(weather.datetimeEpoch),
                style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            )

            WeatherStateImage(
                imageUrl, modifier = Modifier.padding(16.dp),
                contentDescription = stringResource(id = R.string.description_weeather_image),
                contentScale = ContentScale.Crop,
                size = 40
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "${weather.temp.toInt()}$temperatureUnit",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row() {
                Text(
                    text = "Min ${weather.tempmin.toInt()}$temperatureUnit",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Max ${weather.tempmax.toInt()}$temperatureUnit",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

