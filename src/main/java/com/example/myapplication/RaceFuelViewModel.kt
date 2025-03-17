package com.example.srfuelcalc

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RaceFuelViewModel : ViewModel() {
    private val _calculatedFuel = MutableStateFlow(0f)
    val calculatedFuel: StateFlow<Float> = _calculatedFuel.asStateFlow()

    fun calculateFuel(
        isTimeBasedRace: Boolean,
        raceTime: String,
        numberOfLaps: String,
        avgLapTime: String,
        fuelPerLap: String
    ) {
        val fuelPerLapValue = fuelPerLap.toFloatOrNull() ?: 0f
        
        val totalLaps = if (isTimeBasedRace) {
            val raceTimeMinutes = raceTime.toFloatOrNull() ?: 0f
            val avgLapTimeSeconds = avgLapTime.toFloatOrNull() ?: 1f
            (raceTimeMinutes * 60) / avgLapTimeSeconds
        } else {
            numberOfLaps.toFloatOrNull() ?: 0f
        }

        // Add 1 lap safety margin
        _calculatedFuel.value = (totalLaps + 1) * fuelPerLapValue
    }
} 