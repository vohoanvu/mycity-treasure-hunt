package com.example.mycityapp

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycityapp.model.Location
import com.example.mycityapp.ui.treasurehuntscreens.ClueScreen
import com.example.mycityapp.ui.treasurehuntscreens.StartScreen
import com.example.mycityapp.ui.treasurehuntviewmodels.GameViewModel
import com.example.mycityapp.ui.treasurehuntviewmodels.TimerViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import android.util.Log
import com.example.mycityapp.ui.treasurehuntscreens.FoundItScreen
import java.lang.StrictMath.pow
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

/* Assignment 5 Demo
TreasureHuntGameApp.kt
Vu Vo / vovu@oregonstate.edu
CS 492 / Oregon State University
*/

enum class TreasureHuntGameScreen {
    Home, Clue, Found
}

@Composable
fun TreasureHuntGameApp(
    clockVM: TimerViewModel = viewModel(),
    gameVM: GameViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val gameState by gameVM.uiState.collectAsState()
    val gameClock by clockVM.clockState.collectAsState()

    var showWrongLocationDialog by remember { mutableStateOf(false) }
    var showLoadingLocationIndicator by remember { mutableStateOf(false) }

    val activity = LocalContext.current as MainActivity
    activity.checkPermissionsAndRequestLocationUpdates()
    val context = LocalContext.current
    val locationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    NavHost(
        navController = navController,
        startDestination = TreasureHuntGameScreen.Home.name,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route=TreasureHuntGameScreen.Home.name) {
            StartScreen(
                onStartButtonClicked = {
                    clockVM.start()
                    navController.navigate(TreasureHuntGameScreen.Clue.name)
                },
                modifier = Modifier.fillMaxSize(),
            )
        }

        composable(route=TreasureHuntGameScreen.Clue.name) {
            ClueScreen(
                onFoundItButtonClicked = {
                    showLoadingLocationIndicator = true
                    gameState.currentClue?.let { it ->
                        checkLocation(it, locationClient, context) {
                            if (it) {
                                showWrongLocationDialog = false
                                showLoadingLocationIndicator = false
                                navController.navigate(TreasureHuntGameScreen.Found.name)
                                clockVM.pause()
                                gameVM.goToNextClue()
                            } else {
                                showLoadingLocationIndicator = false
                                showWrongLocationDialog = true
                            }
                        }
                    } ?: quitGame(
                        { navController.navigate(TreasureHuntGameScreen.Home.name) },
                        { clockVM.reset() },
                        { gameVM.clearGameState() }
                    )
                },
                onQuitDialogConfirm = {
                    navController.navigate(TreasureHuntGameScreen.Home.name)
                    clockVM.reset()
                    gameVM.clearGameState()
                },
                onWrongLocationDialogDismissed = { showWrongLocationDialog = false },
                showLoadingLocationIndicator = showLoadingLocationIndicator,
                atWrongLocation = showWrongLocationDialog,
                gameState = gameState,
                gameClock = gameClock
            )
        }

        composable(route=TreasureHuntGameScreen.Found.name) {
            FoundItScreen(
                gameClock = gameClock,
                gameState = gameState,
                onClickNextClue = {
                    clockVM.start()
                    navController.navigate(TreasureHuntGameScreen.Clue.name)
                },
                onGoHome = {
                    navController.navigate(TreasureHuntGameScreen.Home.name)
                    clockVM.reset()
                    gameVM.clearGameState()
                }
            )
        }
    }
}

@SuppressLint("MissingPermission")
private fun checkLocation(
    clue: Location,
    locationServices: FusedLocationProviderClient,
    context: Context,
    callback: (Boolean) -> Unit
) {
    locationServices.getCurrentLocation(
        Priority.PRIORITY_HIGH_ACCURACY,
        CancellationTokenSource().token
    ).addOnSuccessListener {
        val earthRadiusKm = 6372.8
        val clueLat = context.getString(clue.lat).toDouble()
        val clueLong = context.getString(clue.long).toDouble()
        val dLat = Math.toRadians(clueLat - it.latitude)
        val dLon = Math.toRadians(clueLong - it.longitude)
        val originLat = Math.toRadians(it.latitude)
        val destinationLat = Math.toRadians(clueLat)

        val a = pow(sin(dLat / 2), 2.toDouble()) + pow(sin(dLon / 2), 2.toDouble()) * cos(originLat) * cos(destinationLat)
        val c = 2 * asin(sqrt(a))
        val distance = earthRadiusKm * c
        Log.d("LOC", "$distance")
        Log.d("LOC", "Device Lat:${it.latitude} Device Long:${it.longitude}" )
        Log.d("LOC", "Loc Lat:${clueLat} Loc Long:${clueLong}" )
        callback(distance < 2)
    }
        .addOnFailureListener {
            callback(false)
        }
}

fun quitGame(
    navigate: () -> Unit,
    resetTimer: () -> Unit,
    resetGame: () -> Unit
) {
    navigate()
    resetTimer()
    resetGame()
}