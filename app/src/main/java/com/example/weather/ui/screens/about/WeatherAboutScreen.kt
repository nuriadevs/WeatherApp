package com.example.weather.ui.screens.about

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import com.example.weather.R
import com.example.weather.ui.components.WeatherAppBar


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WeatherAboutScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier.padding(top = 16.dp),

        topBar = {
            WeatherAppBar(
                title = "About",
                navController = navController,
                isIconLocation = false,
                isMainScreen = false,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )

    }, content =  {

        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.padding(20.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                AboutContent()
            }

        }


    }

    )
}


@Composable
fun AboutContent() {
    Column(
        modifier = Modifier
            .padding(top = 15.dp, bottom = 10.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image(
            painter =  painterResource(id = R.drawable.nube),
            contentDescription = stringResource(id = R.string.description_icon),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(100.dp)
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 10.dp)
        )


        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(id = R.string.app_version),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )


    }


    Column(modifier= Modifier.padding(top = 8.dp)) {

        Column(modifier = Modifier.padding(top = 15.dp)) {
            Text(
                text = stringResource(id = R.string.app_description),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(id = R.string.description_text),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                lineHeight = 1.15.em,
                textAlign = TextAlign.Justify,
            )

        }

        Spacer(modifier = Modifier.height(15.dp))
        Column() {
            Text(
                text = stringResource(id = R.string.app_key),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold

            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(id = R.string.app_key_text_1),
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 1.15.em,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
            )
            Text(
                text = stringResource(id = R.string.app_key_text_2),
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 1.15.em,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
            )

        }
        Spacer(modifier = Modifier.height(15.dp))
        Column() {
            Text(
                text = stringResource(id = R.string.app_system),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold

            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(id = R.string.app_system_text_1),
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 1.15.em,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
            )
            Text(
                text =  stringResource(id = R.string.app_system_text_2),
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 1.15.em,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
            )

        }
        Spacer(modifier = Modifier.height(15.dp))
        Column() {
            Text(
                text = stringResource(id = R.string.app_dev_info),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold

            )
            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = stringResource(id = R.string.app_dev_text),
                color = MaterialTheme.colorScheme.secondary,
                lineHeight = 1.15.em,
                textAlign = TextAlign.Justify,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
            )

        }

    }

}

