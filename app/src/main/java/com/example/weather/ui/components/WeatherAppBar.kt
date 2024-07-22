package com.example.weather.ui.components


import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.R
import com.example.weather.ui.navigation.WeatherScreens
import com.example.weather.ui.screens.settings.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppBar(
    isLoading: Boolean = false,
    title: String = "Title",
    icon: ImageVector? = null,
    isMainScreen: Boolean = true,
    isIconLocation: Boolean = true,
    elevation: Dp = 0.dp,
    navController: NavController,
    onAddActionClicked: () -> Unit = {},
    onButtonClicked: () -> Unit = {}
) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {


        CenterAlignedTopAppBar(

            title = {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (isIconLocation) {
                            Icon(
                                imageVector = Icons.Rounded.LocationOn,
                                contentDescription = stringResource(id = R.string.description_icon),
                                modifier = Modifier.size(25.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }


                        Spacer(modifier = Modifier.width(6.dp))

                        Text(
                            text = title, style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            ),
                            maxLines = 1, overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            },

            navigationIcon = {
                if (isMainScreen) {
                    IconButton(onClick = { onButtonClicked.invoke() }) {
                        MenuSample(navController)
                    }
                } else {
                    IconButton(onClick = { onButtonClicked.invoke() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = stringResource(id = R.string.description_icon),
                            modifier = Modifier.size(25.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

            },

            )

    }

}


@Composable
fun MenuSample(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var showExitDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                Icons.Default.MoreVert,
                contentDescription = stringResource(id = R.string.description_icon),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(
                text = {
                    Text(
                        stringResource(id = R.string.about),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                },
                onClick = { navController.navigate(WeatherScreens.WeatherAboutScreen.name) },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = stringResource(id = R.string.description_icon),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
            DropdownMenuItem(
                text = {
                    Text(
                        stringResource(id = R.string.settings),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                },
                onClick = { navController.navigate(WeatherScreens.WeatherSettingsScreen.name) },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Settings,
                        contentDescription = stringResource(id = R.string.description_icon),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            )
            HorizontalDivider()
            DropdownMenuItem(
                text = {
                    Text(
                        stringResource(id = R.string.exitApp),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                },
                onClick = {
                    showExitDialog = true
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.ExitToApp,
                        contentDescription = stringResource(id = R.string.description_icon),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                /*,
                trailingIcon = {
                    Text(
                        "F11",
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.primary
                        )
                    )
                }
                */
            )
        }
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text(text = stringResource(id = R.string.confirmExit)) },
            text = { Text(stringResource(id = R.string.textConfirm)) },
            confirmButton = {
                Button(
                    onClick = {
                        settingsViewModel.deleteAllUnits()
                        (context as? Activity)?.finish()
                    }
                ) {
                    Text(stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showExitDialog = false
                    }
                ) {
                    Text(stringResource(id = R.string.no))
                }
            }
        )
    }
}

