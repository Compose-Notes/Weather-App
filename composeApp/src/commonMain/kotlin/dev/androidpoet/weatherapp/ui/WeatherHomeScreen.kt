package dev.androidpoet.weatherapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

private data class HourlyUi(val time: String, val temp: Int, val icon: androidx.compose.ui.graphics.vector.ImageVector)
private data class DailyUi(val day: String, val min: Int, val max: Int, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun WeatherHomeScreen(contentPadding: PaddingValues = PaddingValues()) {
    var query by remember { mutableStateOf("") }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(contentPadding),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            SearchBar(
                value = query,
                onValueChange = { query = it },
                onSearch = { /* TODO hook into VM */ }
            )
        }
        item { CurrentWeatherCard() }
        item { HourlyForecastRow(items = demoHourly()) }
        item { DailyForecastList(items = demoDaily()) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSearch: () -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
        placeholder = { Text("Search city…") },
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            if (value.isNotBlank()) {
                TextButton(onClick = onSearch) { Text("Go") }
            }
        }
    )
}

@Composable
private fun CurrentWeatherCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors()
    ) {
        Row(
            Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Replace with real icon when wired
            Icon(
                imageVector = Icons.Default.WbSunny,
                contentDescription = "Condition",
                modifier = Modifier.size(48.dp)
            )
            Spacer(Modifier.width(16.dp))
            Column(Modifier.weight(1f)) {
                Text("New Delhi", style = MaterialTheme.typography.titleMedium)
                Text("Mostly Sunny", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text("31°", style = MaterialTheme.typography.displaySmall)
                Text("H:34°  L:27°", style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun HourlyForecastRow(items: List<HourlyUi>) {
    Column {
        Text("Hourly", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            items(items) { hour ->
                ElevatedCard {
                    Column(
                        Modifier.padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(hour.time, style = MaterialTheme.typography.labelMedium)
                        Spacer(Modifier.height(8.dp))
                        Icon(imageVector = hour.icon, contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.height(8.dp))
                        Text("${hour.temp}°", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

@Composable
private fun DailyForecastList(items: List<DailyUi>) {
    Column {
        Text("Next 7 Days", style = MaterialTheme.typography.titleMedium)
        Spacer(Modifier.height(8.dp))
        ElevatedCard(Modifier.fillMaxWidth()) {
            Column {
                items.forEach { day ->
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(day.day, Modifier.weight(1f), maxLines = 1, overflow = TextOverflow.Ellipsis)
                        Icon(imageVector = day.icon, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(12.dp))
                        Text("${day.min}° / ${day.max}°", style = MaterialTheme.typography.bodyMedium)
                    }
                    if (day != items.last()) Divider()
                }
            }
        }
    }
}

private fun demoHourly() = listOf(
    HourlyUi("Now", 31, Icons.Default.WbSunny),
    HourlyUi("13:00", 32, Icons.Default.WbSunny),
    HourlyUi("14:00", 33, Icons.Default.WbSunny),
    HourlyUi("15:00", 33, Icons.Default.Cloud),
    HourlyUi("16:00", 32, Icons.Default.Cloud),
)

private fun demoDaily() = listOf(
    DailyUi("Mon", 27, 34, Icons.Default.WbSunny),
    DailyUi("Tue", 27, 33, Icons.Default.Cloud),
    DailyUi("Wed", 26, 32, Icons.Default.Cloud),
    DailyUi("Thu", 26, 31, Icons.Default.Cloud),
    DailyUi("Fri", 26, 33, Icons.Default.WbSunny),
    DailyUi("Sat", 27, 34, Icons.Default.WbSunny),
    DailyUi("Sun", 27, 35, Icons.Default.WbSunny),
)

@Composable
private fun WeatherHomePreview() = MaterialTheme { WeatherHomeScreen() }
