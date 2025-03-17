package com.example.srfuelcalc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import android.widget.Toast
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RaceFuelCalculatorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    RaceFuelCalculatorScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RaceFuelCalculatorScreen(
    viewModel: RaceFuelViewModel = viewModel()
) {
    var isTimeBasedRace by remember { mutableStateOf(true) }
    var raceTime by remember { mutableStateOf("") }
    var numberOfLaps by remember { mutableStateOf("") }
    var avgLapTime by remember { mutableStateOf("") }
    var fuelPerLap by remember { mutableStateOf("") }
    var calculatedFuel by remember { mutableStateOf(0f) }
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        
        Text(
            text = "SR Fuel Calculator",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Race Type Selection
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilterChip(
                selected = isTimeBasedRace,
                onClick = { isTimeBasedRace = true },
                label = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Timer,
                                contentDescription = "Time",
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                "Time Based",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                },
                modifier = Modifier
                    .height(70.dp)
                    .width(160.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            FilterChip(
                selected = !isTimeBasedRace,
                onClick = { isTimeBasedRace = false },
                label = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Speed,
                                contentDescription = "Laps",
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                "Lap Based",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                },
                modifier = Modifier
                    .height(70.dp)
                    .width(160.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input Fields
        if (isTimeBasedRace) {
            TextField(
                value = raceTime,
                onValueChange = { raceTime = it },
                label = { Text("Race Duration (minutes)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.headlineMedium,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )
        } else {
            TextField(
                value = numberOfLaps,
                onValueChange = { numberOfLaps = it },
                label = { Text("Number of Laps") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                textStyle = MaterialTheme.typography.headlineMedium,
                colors = TextFieldDefaults.colors(
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent
                )
            )
        }

        TextField(
            value = avgLapTime,
            onValueChange = { avgLapTime = it },
            label = { Text("Lap Time (M.SS.s)") },
            placeholder = { Text("Example: 1.25.0") },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = MaterialTheme.typography.headlineMedium,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )

        TextField(
            value = fuelPerLap,
            onValueChange = { fuelPerLap = it },
            label = { Text("Fuel per Lap (liters)") },
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            textStyle = MaterialTheme.typography.headlineMedium,
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                try {
                    val lapTimeInSeconds = parseLapTime(avgLapTime)
                    calculatedFuel = if (isTimeBasedRace) {
                        val totalLaps = (raceTime.toFloatOrNull() ?: 0f) * 60 / lapTimeInSeconds
                        (totalLaps + 1) * (fuelPerLap.toFloatOrNull() ?: 0f)
                    } else {
                        ((numberOfLaps.toFloatOrNull() ?: 0f) + 1) * (fuelPerLap.toFloatOrNull() ?: 0f)
                    }
                } catch (e: Exception) {
                    Toast.makeText(context, "Invalid lap time format. Use M.SS.s (e.g., 1.25.0)", Toast.LENGTH_LONG).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Text(
                "Calculate Fuel Needed",
                style = MaterialTheme.typography.titleLarge
            )
        }

        Button(
            onClick = {
                raceTime = ""
                numberOfLaps = ""
                avgLapTime = ""
                fuelPerLap = ""
                calculatedFuel = 0f
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Red,
                contentColor = Color.White
            )
        ) {
            Text(
                "Clear All",
                style = MaterialTheme.typography.titleLarge
            )
        }

        // Result Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.LocalGasStation,
                    contentDescription = "Fuel",
                    modifier = Modifier.size(48.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Required Fuel",
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "%.2f Liters".format(calculatedFuel),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

fun parseLapTime(lapTime: String): Float {
    try {
        val parts = lapTime.split(".")
        if (parts.size != 3) throw IllegalArgumentException("Invalid format")
        
        val minutes = parts[0].toFloat()
        val seconds = parts[1].toFloat()
        val tenths = parts[2].toFloat()
        
        if (seconds >= 60) throw IllegalArgumentException("Seconds should be less than 60")
        if (tenths >= 10) throw IllegalArgumentException("Tenths should be a single digit")
        
        return minutes * 60 + seconds + tenths / 10
    } catch (e: Exception) {
        throw IllegalArgumentException("Invalid lap time format")
    }
}

@Composable
fun RaceFuelCalculatorTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        typography = Typography(
            headlineMedium = MaterialTheme.typography.headlineMedium.copy(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium
            ),
            titleLarge = MaterialTheme.typography.titleLarge.copy(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium
            ),
            titleMedium = MaterialTheme.typography.titleMedium.copy(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium
            ),
            headlineLarge = MaterialTheme.typography.headlineLarge.copy(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Medium
            ),
            bodyLarge = MaterialTheme.typography.bodyLarge.copy(
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Normal
            )
        ),
        content = content
    )
}