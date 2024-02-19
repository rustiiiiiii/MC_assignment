package com.example.mobile_computing_assignment

import android.widget.Toast
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll


data class Stop(
    val name: String,
    val distanceToNext: Double
)

class MainActivity : ComponentActivity() {
    private val normalstop = listOf(
        Stop("Pitampura", 5.5),
        Stop("KOHAT ENCLAVE", 12.0),
        Stop("NETAJI SUBHASH PLACE", 8.0),
        Stop("KESHAVPURAM", 6.7),
        Stop("KANHAIYA NAGAR ", 4.9),
        Stop("INDER LOK", 5.4),
        Stop("SHASTRI NAGAR", 6.7),
        Stop("PRATAP NAGAR", 2.8),
        Stop("PULBANGASH", 3.0),
        Stop("TIS HAZARI", 4.7),
        Stop("KASHMERE GATE", 9.2),
        Stop("LAL QUILA", 5.0),
    )
    private val lazystop = listOf(
        Stop("Pitampura", 5.5),
        Stop("KOHAT ENCLAVE", 12.0),
        Stop("NETAJI SUBHASH PLACE", 8.0),
        Stop("KESHAVPURAM", 6.7),
        Stop("KANHAIYA NAGAR ", 4.9),
        Stop("INDER LOK", 5.4),
        Stop("SHASTRI NAGAR", 6.7),
        Stop("PRATAP NAGAR", 2.8),
        Stop("PULBANGASH", 3.0),
        Stop("TIS HAZARI", 4.7),
        Stop("KASHMERE GATE", 9.2),
        Stop("LAL QUILA", 5.0),
        Stop("JAMA MASJID", 7.0),
        Stop("DELHI GATE", 6.6),
        Stop("ITO ", 5.0),
        Stop("MANDI HOUSE", 4.0),
        Stop("SUPREME COURT", 9.0),
        Stop("INDRAPRASTHA", 6.0),
        Stop("YAMUNA BANK", 7.7),
        Stop("AKSHARDHAM", 6.5),
        Stop("MAYUR VIHAR PHASE-I", 4.6),
        Stop("NEW ASHOK NAGAR", 7.7),
        Stop("NOIDA SECTOR 15", 6.4),
        Stop("NOIDA SECTOR 16", 7.2),
        Stop("NOIDA SECTOR 18", 5.6),
        Stop("BOTANICAL GARDEN", 7.5),


    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TrackerApp()
        }
    }

    @Composable
    fun TrackerApp() {


        var currentStopIndex by remember { mutableStateOf(0) }
        var useLazyColumn by remember { mutableStateOf(false) }
        var displayInMiles by remember { mutableStateOf(false) }
        val stops = if (useLazyColumn) lazystop else normalstop

        LaunchedEffect(useLazyColumn) {
            Msg(useLazyColumn)
        }

        Scaffold(
            topBar = {
                TrackerTopBar(
                    useLazyColumn = useLazyColumn,
                    onUseLazyColumnChange = { useLazyColumn = it },
                    onResetClick = { currentStopIndex = 0 }


                )
            },
            bottomBar = {
                Column {
                    Distance(currentStopIndex, stops, displayInMiles)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Button(
                            onClick = {
                                currentStopIndex = (currentStopIndex + 1) % stops.size
                            },
                            modifier = Modifier
                                .weight(0.75f)
                                .padding(end = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF81D4FA)
                            )
                        ) {
                            Text("Next Stop")
                        }

                        Button(
                            onClick = { displayInMiles = !displayInMiles },
                            modifier = Modifier
                                .weight(0.25f)
                                .padding(start = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF81D4FA) // Light blue color
                            )
                        ) {
                            Text(if (displayInMiles) "km" else "Miles")
                        }
                    }
                }
            },
            content = { paddingValues ->
                Column(modifier = Modifier.padding(paddingValues)) {
                    val progress =
                        (currentStopIndex.toFloat() / (stops.size - 1).toFloat()).coerceIn(0f, 1f)
                    LinearProgressIndicator(
                        progress = progress,
                        color = Color(0xFF81D4FA),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    if (useLazyColumn) {
//                        Message(useLazyColumn)
                        StopsLazyColumn(stops, currentStopIndex, displayInMiles)
                    } else {
//                        Message(useLazyColumn)
                        StopsColumn(stops, currentStopIndex, displayInMiles)

                    }
                }
            }
        )
    }

    @Composable
    @OptIn(ExperimentalMaterial3Api::class)
    fun TrackerTopBar(
        useLazyColumn: Boolean,
        onUseLazyColumnChange: (Boolean) -> Unit,
        onResetClick: () -> Unit

    ) {
        TopAppBar(
            title = { Text("AppyJourney") },
            actions = {
                // Toggle button for switching between Column and LazyColumn
                Switch(
                    checked = useLazyColumn,
                    onCheckedChange = onUseLazyColumnChange

                )
                // Reset button to restart the progress
                IconButton(onClick = onResetClick) {
                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Reset"
                    )
                }
            }
        )
    }
    @Composable
    fun StopsColumn(stops: List<Stop>, currentStopIndex: Int, displayInMiles: Boolean) {
        // Create a ScrollState for the column
        val scrollState = rememberScrollState()

        // Apply the verticalScroll modifier to the Column
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            stops.forEachIndexed { index, stop ->
                StopItem(stop, index == currentStopIndex,displayInMiles)
            }
        }
    }
    @Composable
    fun StopsLazyColumn(stops: List<Stop>, currentStopIndex: Int, displayInMiles: Boolean) {
        LazyColumn {
            itemsIndexed(stops) { index, stop ->
                StopItem(stop, index == currentStopIndex,displayInMiles)
            }
        }
    }


    @Composable
    fun StopItem(stop: Stop, isCurrent: Boolean,displayInMiles: Boolean) {
        // Define a light blue color
        val lightBlue = Color(0xFF81D4FA) // This is a light blue color.

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 4.dp),
            // Set the container color based on whether the stop is the current one
            colors = CardDefaults.cardColors(
                containerColor = if (isCurrent) lightBlue else MaterialTheme.colorScheme.surface
            ),
            elevation = if (isCurrent) CardDefaults.cardElevation(defaultElevation = 4.dp) else CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = stop.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = if (displayInMiles) "%.1f mi".format(stop.distanceToNext * 0.621371) else "${stop.distanceToNext} km",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }


    fun Msg(toggleState: Boolean) {
        // Side effect that reacts to changes in toggleState
        Toast.makeText(
            this,
            if (toggleState) "LazyColumn is implemented" else "Normal Column is implemented",
            Toast.LENGTH_SHORT
        ).show()
    }
    @Composable
    fun Distance(currentStopIndex: Int, stops: List<Stop>, displayInMiles: Boolean) {
        val totalDistanceKm = stops.sumOf { it.distanceToNext }
        val distanceCoveredKm = stops.take(currentStopIndex).sumOf { it.distanceToNext }
        val distanceLeftKm = totalDistanceKm - distanceCoveredKm

        val kmToMilesFactor = 0.621371

        val distanceCovered = if (displayInMiles) distanceCoveredKm * kmToMilesFactor else distanceCoveredKm
        val distanceLeft = if (displayInMiles) distanceLeftKm * kmToMilesFactor else distanceLeftKm
        val unit = if (displayInMiles) "mi" else "km"

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Distance Covered: ${"%.1f".format(distanceCovered)} $unit")
            Text("Distance Left: ${"%.1f".format(distanceLeft)} $unit")
        }
    }

}




