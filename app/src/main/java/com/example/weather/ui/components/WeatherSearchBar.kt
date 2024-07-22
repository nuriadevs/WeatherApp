package com.example.weather.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.weather.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onCitySelected: (String) -> Unit,
    searchQueryState: MutableState<String>,
    placeholder: String
) {
    var text by remember { mutableStateOf(query) }
    var active by remember { mutableStateOf(false) }
    val searchHistory = remember { mutableStateListOf<String>() }


    SearchBar(
        modifier = Modifier
            .padding(bottom = 10.dp)
            .fillMaxWidth(),
        query = text,
        onQueryChange = {
            text = it
            onQueryChange(it)
            searchQueryState.value = it
        },
        onSearch = {
            searchHistory.add(text)
            active = false
            onCitySelected(text)
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(id = R.string.description_icon),
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            if (active) {
                Icon(
                    modifier = Modifier.clickable {
                        if (text.isNotEmpty()) {
                            text = ""
                        } else {
                            active = false
                        }
                    },
                    imageVector = Icons.Default.Close,
                    contentDescription = stringResource(id = R.string.description_icon),
                    tint = MaterialTheme.colorScheme.secondary
                )
            }
        }
    ) {
        searchHistory.forEach { city ->
            if (city.isNotEmpty()) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 14.dp)
                    .clickable {
                        text = city
                        onQueryChange(city)
                        searchQueryState.value = city
                        onCitySelected(city)
                        active = false

                    }) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = city)
                }
            }
        }
        Text(
            modifier = Modifier
                .padding(all = 14.dp)
                .fillMaxWidth()
                .clickable { searchHistory.clear() },
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            text = stringResource(id = R.string.clearHistory)
        )
    }
}

