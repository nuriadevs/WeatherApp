package com.example.weather.ui.screens.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.weather.ui.components.WeatherAppBar
import com.example.weather.data.database.Unit
import com.example.weather.R



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun WeatherSettingsScreen(
    navController: NavController,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {

    val measurementUnits = listOf("us" to "°F", "metric" to "°C")
    val choiceFromDatabase by settingsViewModel.unitList.collectAsState()
    val defaultChoice = if (choiceFromDatabase.isNullOrEmpty()) measurementUnits[0].first else choiceFromDatabase[0].unitGroup
    var choiceState by remember { mutableStateOf(defaultChoice) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedUnit by remember { mutableStateOf(defaultChoice) }

    Scaffold(
        modifier = Modifier.padding(top = 16.dp),
        topBar = {
            WeatherAppBar(
                title = stringResource(id = R.string.settings),
                navController = navController,
                isIconLocation = false,
                isMainScreen = false,
                onButtonClicked = {
                    navController.popBackStack()
                }
            )
        },
        content = {
            Surface(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = stringResource(id = R.string.changeUnit))

                    Spacer(modifier = Modifier.height(16.dp))

                    Column {
                        measurementUnits.forEach { unit ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        selectedUnit = unit.first
                                    }
                            ) {
                                RadioButton(
                                    selected = selectedUnit == unit.first,
                                    onClick = { selectedUnit = unit.first }
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(text = unit.second)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            settingsViewModel.deleteAllUnits()
                            settingsViewModel.insertUnit(Unit(selectedUnit))
                            showDialog = true
                        },
                        modifier = Modifier
                            .padding(3.dp)
                            .align(Alignment.CenterHorizontally),
                        shape = RoundedCornerShape(34.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.save),
                            modifier = Modifier.padding(4.dp),
                            fontSize = 17.sp
                        )
                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text(text = stringResource(id = R.string.selectionSave)) },
                            text = { Text("The selected unit $selectedUnit has been saved successfully.") },
                            confirmButton = {
                                Button(
                                    onClick = { showDialog = false }
                                ) {
                                    Text(text = stringResource(id = R.string.ok))
                                }
                            }
                        )
                    }
                }
            }
        }
    )
}

