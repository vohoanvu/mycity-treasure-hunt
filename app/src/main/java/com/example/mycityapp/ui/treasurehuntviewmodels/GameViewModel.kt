package com.example.mycityapp.ui.treasurehuntviewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycityapp.data.TreasureHuntDataSource
import com.example.mycityapp.model.GameState
import com.example.mycityapp.model.Location
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class GameViewModel : ViewModel() {
    private val allClues = TreasureHuntDataSource.allLocations

    private val _uiState = MutableStateFlow(
        GameState(
            allClues,
            allClues[0],
            null,
            0,
            allClues.size
        )
    )
    val uiState: StateFlow<GameState> = _uiState.asStateFlow()

    fun advanceToNextClue() {
        _uiState.update { currentState ->
            var nextLocation: Location? = null
            var index = currentState.currentClueIndex
            index++
            if (index < currentState.totalClues) {
                nextLocation = currentState.cluesRemaining[index]
            }
            currentState.copy(
                currentClue = nextLocation,
                currentClueIndex = index,
                foundClue = currentState.currentClue
            )
        }
    }

    fun clearGameState() {
        _uiState.update { currentState ->
            currentState.copy(
                cluesRemaining = allClues,
                currentClue = allClues[0],
                currentClueIndex = 0,
                totalClues = allClues.size,
                foundClue = null
            )
        }
    }
}

class TimerViewModel : ViewModel()
{
    private val _clockState = MutableStateFlow(0L)
    val clockState: StateFlow<Long> = _clockState.asStateFlow()
    private var timerJob: Job? = null

    fun start() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                _clockState.value++
                delay(1000)
            }
        }
    }

    fun pause() {
        timerJob?.cancel()
    }

    fun reset() {
        _clockState.value = 0
        timerJob?.cancel()
    }
}

fun Long.formatTime(): String {
    val seconds = this % 60
    val minutes = (this / 60) % 60
    return String.format(Locale.getDefault(),"%02d : %02d", minutes, seconds)
}