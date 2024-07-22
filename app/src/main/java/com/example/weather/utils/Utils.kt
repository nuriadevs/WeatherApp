package com.example.weather.utils

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.size.Size
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

fun formatDate(timestamp: Int): String{
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)

}


@RequiresApi(Build.VERSION_CODES.O)
fun getHour(epoch: Long): String {
    val fechaHora = LocalDateTime.ofInstant(Instant.ofEpochSecond(epoch), ZoneId.systemDefault())
    val formato = DateTimeFormatter.ofPattern("HH:mm:ss")  // Define el formato de salida como solo hora:minuto:segundo
    return fechaHora.format(formato)
}


@RequiresApi(Build.VERSION_CODES.O)
fun getDayOfWeek(timestamp: Long): String {
    // Convertir el timestamp a LocalDate
    val date = Instant.ofEpochSecond(timestamp)
        .atZone(ZoneId.systemDefault())
        .toLocalDate()

    // Obtener el día de la semana
    val dayOfWeek = date.dayOfWeek

    // Devolver el nombre del día en inglés
    return when (dayOfWeek) {
        java.time.DayOfWeek.MONDAY -> "Monday"
        java.time.DayOfWeek.TUESDAY -> "Tuesday"
        java.time.DayOfWeek.WEDNESDAY -> "Wednesday"
        java.time.DayOfWeek.THURSDAY -> "Thursday"
        java.time.DayOfWeek.FRIDAY -> "Friday"
        java.time.DayOfWeek.SATURDAY -> "Saturday"
        java.time.DayOfWeek.SUNDAY -> "Sunday"
        else -> throw IllegalArgumentException("Invalid day of week")
    }
}

fun formData(timestamp: Int): String{
    val sdf = SimpleDateFormat("EEE, MMM d")
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

fun formDataComplete(timestamp: Int): String {
    val sdf = SimpleDateFormat("EEEE, MMM d", Locale.getDefault())
    val date = Date(timestamp.toLong() * 1000)
    return sdf.format(date)
}

@Composable
fun WeatherStateImage(
    imageUrl: String,
    modifier: Modifier,
    contentDescription: String = "",
    contentScale: ContentScale,
    size: Int
) {


    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .decoderFactory(SvgDecoder.Factory())
            .data(imageUrl)
            .size(size)
            .build()
    )
    Image(
        painter = painter,
        contentDescription = null,
        contentScale = contentScale,
        modifier = modifier.size(size.dp) // Apply the size modifier
    )


/*
    Image(
        painter = rememberAsyncImagePainter(imageUrl),
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier.size(size.dp)
    )
*/
}


/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarExample(
    query: String,
    onQueryChange: (String) -> Unit,
    onCitySelected: (String) -> Unit,
    searchQueryState: MutableState<String>,
    placeholder: String
) {
    var text by remember { mutableStateOf(query) }
    var active by remember { mutableStateOf(false) }

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
            active = false
            onCitySelected(text)
        },
        active = active,
        onActiveChange = { active = it },
        placeholder = { Text(text = placeholder) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
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
                    contentDescription = null
                )
            }
        }
    ) {
        // No hay contenido dentro del bloque del cuerpo ya que no se guarda el historial.
    }
}
 */